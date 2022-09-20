USE stefaninifood;
GO

-- INSERE 2 CLIENTES
EXEC dbo.proc_insert_customer 'José Layrton', 70541197460;
EXEC dbo.proc_insert_customer 'José Karlos', 55871123066;
-- Dá erro ao inserir: EXEC dbo.proc_insert_customer 'José Karlos', '12345678901';

-- INSERE 2 EMPRESAS
EXEC dbo.proc_insert_company 'Açaí do Gaguinho', '50357090000140';
EXEC dbo.proc_insert_company 'Toinho Lanches', '15285087000168';
--Dá erro ao inserir: EXEC dbo.proc_insert_company 'Toinho Lanches', '12345678901234';

--INSERE ENDEREÇOS PARA CLIENTES
EXEC dbo.proc_insert_address 'Rua frei alberto, 365', 'Paraíba', 'Brasil', '58388000', 1, 0;
EXEC dbo.proc_insert_address 'Rua José Marinho Falcão, 105', 'Paraíba', 'Brasil', '58430768', 2, 0;

-- INSERE ENDEREÇOS PARA EMPRESAS
EXEC dbo.proc_insert_address 'Rua frei alberto, 100', 'Paraíba', 'Brasil', '58388000', 1, 1;
EXEC dbo.proc_insert_address 'Rua José Marinho Falcão, 200', 'Paraíba', 'Brasil', '58430768', 2, 1;

INSERT INTO wallet (customer_id, cash) VALUES (1, 500);

INSERT INTO credit_card (wallet_id, card_number, expiration_date, cvv) VALUES (1, '5393964922019055', '2023-09-20', '152');

INSERT INTO product(name, description, price, company_id) 
	 VALUES ('Açaí', 'Açaí completo.', 7.00, 1),
			('Coxinha', 'Coxinha de frango com catupiry.', 3.00, 2),
			('Água', 'Agua mineral Itacoatiara', 2.00, 1);

INSERT INTO order_details(customer_id, payment_method, total_price, order_status) 
	 VALUES (1, 'CREDIT CARD', 0, 'PROCESSING'),
			(1, 'PIX', 0, 'PROCESSING'),
			(2, 'CREDIT CARD', 0, 'PROCESSING');

GO

INSERT INTO order_product(order_id, product_id, quantity) 
	 VALUES (1, 1, 3),
			(2, 1, 1),
			(3, 2, 5);

EXEC dbo.proc_insert_order_product 1, 1, 3;
EXEC dbo.proc_insert_order_product 1, 3, 2;

GO
-------------------------------------------------------------------

CREATE OR ALTER VIEW vw_customer_orders_quantity AS
	SELECT TOP (100) customer.name, COUNT(order_details.customer_id) AS orders, SUM (order_product.quantity) AS product_quantity, SUM (order_details.total_price) AS money_spend
	FROM customer
	JOIN order_details ON order_details.customer_id = customer.id
	JOIN order_product ON order_details.id = order_product.order_id
	GROUP BY customer.name
	ORDER BY orders DESC

GO
-------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE proc_update_status AS
BEGIN
	UPDATE order_details SET order_status = 'CLOSED'
	 WHERE DATEDIFF(MINUTE, order_details.createdAt, SYSDATETIME()) >= 1
	   AND order_status LIKE 'PROCESSING'
END

GO
-------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE proc_insert_customer @name VARCHAR(255), @cpf VARCHAR(11)
AS
BEGIN
BEGIN TRANSACTION
	IF [dbo].[fncValida_CPF](@cpf) = 1
		BEGIN
			INSERT INTO customer(name, cpf) VALUES (@name, @cpf);
			PRINT 'Usuário cadastrado.';
		END
	ELSE
		BEGIN
			PRINT 'CPF invalido.';
			ROLLBACK;
		END

IF @@TRANCOUNT > 0 COMMIT;

END
GO
-------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE proc_insert_company @name VARCHAR(255), @cnpj VARCHAR(14)
AS
BEGIN
BEGIN TRANSACTION
	IF [dbo].[fncValida_CNPJ](@cnpj) = 1
		BEGIN
			INSERT INTO company(name, cnpj) VALUES (@name, @cnpj);
			PRINT 'Empresa cadastrada.';
		END
	ELSE
		BEGIN
			PRINT 'CNPJ inválido.';
			ROLLBACK;
		END
IF @@TRANCOUNT > 0 COMMIT;
END
GO
--------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE proc_insert_address @logradouro VARCHAR(255), @estado VARCHAR(255), @pais VARCHAR(255), @cep CHAR(8), @id INTEGER, @type BIT
AS
BEGIN
BEGIN TRANSACTION
	IF dbo.fncVerifica_Cep(@cep) = 1
		BEGIN TRY
			IF @type = 0
			BEGIN
				INSERT INTO address(logradouro, estado, pais, cep, customer_id)
				VALUES (@logradouro, @estado, @pais, @cep, @id);
				PRINT 'Endereço do cliente foi cadastrado';
			END
			IF @type = 1
			BEGIN
				INSERT INTO address(logradouro, estado, pais, cep, company_id)
				VALUES (@logradouro, @estado, @pais, @cep, @id);
				PRINT 'Endereço da empresa foi cadastrado';
			END
		END TRY
		BEGIN CATCH
			PRINT 'Erro ao cadastrar endereço.';
			ROLLBACK;
		END CATCH
	ELSE
		BEGIN
			PRINT 'CEP INVÁLIDO.';
			ROLLBACK;
		END
IF @@TRANCOUNT > 0 COMMIT;
END

GO
--------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE proc_insert_order_product @order_id INTEGER, @product_id INTEGER, @quantity INTEGER
AS
BEGIN
BEGIN TRANSACTION
	BEGIN TRY
		DECLARE @price REAL = dbo.fncCopy_price(@product_id);

		INSERT INTO order_product (order_id, product_id, price, quantity) VALUES (@order_id, @product_id, @price, @quantity);

		UPDATE order_details SET total_price += @price * @quantity WHERE order_details.id = @order_id;
	END TRY

	BEGIN CATCH
		PRINT 'ERRO AO ADICIONAR O PRODUTO';
		ROLLBACK;
	END CATCH

IF @@TRANCOUNT > 0 COMMIT;
END

GO
--------------------------------------------------------------------------------------

CREATE OR ALTER FUNCTION fncCopy_price(@id INTEGER)
RETURNS REAL AS 
BEGIN
	DECLARE @price REAL;
	--SET @price = (SELECT price FROM product WHERE product.id = @id);
	SELECT @price = price FROM product WHERE product.id = @id;
	RETURN @price
END

GO
--------------------------------------------------------------------------------------

CREATE OR ALTER FUNCTION fncVerifica_Cep (@cep CHAR(8))
RETURNS BIT
AS 
BEGIN
	DECLARE @chr CHAR, @tamanho INT
	-- Verifica se possui 8 caracteres
	IF (LEN(@cep) < 8)
		RETURN 0
	WHILE (LEN(@cep) > 0)
	BEGIN
		SELECT @tamanho = LEN(@cep), @chr = LEFT(@cep,1)
		-- Verifica se o número informado possui apenas números
		IF CHARINDEX(@chr,'0123456789') = 0
		BEGIN
			RETURN 0
			BREAK
		END
		SET @cep = STUFF(@cep,1,1,'') -- retira o primeiro dígito
	END
	RETURN 1
END

GO
---------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER FUNCTION fncValida_CPF(@cpf CHAR(11))
RETURNS BIT -- 1 = válido, 0 = inválido
WITH SCHEMABINDING
BEGIN

	DECLARE
		@Contador_1 INT,
		@Contador_2 INT,
		@Digito_1 INT,
		@Digito_2 INT,
		@Nr_Documento_Aux VARCHAR(11)

	-- Remove espaços em branco
	SET @Nr_Documento_Aux = LTRIM(RTRIM(@cpf))
	SET @Digito_1 = 0


	-- Remove os números que funcionam como validação para CPF, pois eles "passam" pela regra de validação
	IF (@Nr_Documento_Aux IN ('00000000000', '11111111111', '22222222222', '33333333333', '44444444444', '55555555555', '66666666666', '77777777777', '88888888888', '99999999999', '12345678909'))
		RETURN 0

	-- Verifica se possui apenas 11 caracteres
	IF (LEN(@Nr_Documento_Aux) <> 11)
		RETURN 0
	ELSE 
	BEGIN

		-- Cálculo do segundo dígito
		SET @Nr_Documento_Aux = SUBSTRING(@Nr_Documento_Aux, 1, 9)

		SET @Contador_1 = 2

		WHILE (@Contador_1 < = 10)
		BEGIN 
			SET @Digito_1 = @Digito_1 + (@Contador_1 * CAST(SUBSTRING(@Nr_Documento_Aux, 11 - @Contador_1, 1) as int))
			SET @Contador_1 = @Contador_1 + 1
		end 

		SET @Digito_1 = @Digito_1 - (@Digito_1/11)*11

		IF (@Digito_1 <= 1)
			SET @Digito_1 = 0
		ELSE 
			SET @Digito_1 = 11 - @Digito_1

		SET @Nr_Documento_Aux = @Nr_Documento_Aux + CAST(@Digito_1 AS VARCHAR(1))

		IF (@Nr_Documento_Aux <> SUBSTRING(@cpf, 1, 10))
			RETURN 0
		ELSE BEGIN 
		
			-- Cálculo do segundo dígito
			SET @Digito_2 = 0
			SET @Contador_2 = 2

			WHILE (@Contador_2 < = 11)
			BEGIN 
				SET @Digito_2 = @Digito_2 + (@Contador_2 * CAST(SUBSTRING(@Nr_Documento_Aux, 12 - @Contador_2, 1) AS INT))
				SET @Contador_2 = @Contador_2 + 1
			END

			SET @Digito_2 = @Digito_2 - (@Digito_2/11)*11

			IF (@Digito_2 < 2)
				SET @Digito_2 = 0
			ELSE 
				SET @Digito_2 = 11 - @Digito_2

			SET @Nr_Documento_Aux = @Nr_Documento_Aux + CAST(@Digito_2 AS VARCHAR(1))

			IF (@Nr_Documento_Aux <> @cpf)
				RETURN 0
		END
	END 
	RETURN 1
END

GO
----------------------------------------------------------------------------------------------------

CREATE OR ALTER FUNCTION fncValida_CNPJ ( @cnpj VARCHAR(14) )
RETURNS BIT
AS
BEGIN
	DECLARE
		@INDICE INT,
		@SOMA INT,
		@DIG1 INT,
		@DIG2 INT,
		@VAR1 INT,
		@VAR2 INT,
		@RESULTADO CHAR(1)
 
	SET @SOMA = 0
	SET @INDICE = 1
	SET @RESULTADO = 0
	SET @VAR1 = 5 /* 1a Parte do Algorítimo começando de "5" */

	WHILE ( @INDICE < = 4 )
	BEGIN
		SET @SOMA = @SOMA + CONVERT(INT, SUBSTRING(@CNPJ, @INDICE, 1)) * @VAR1
		SET @INDICE = @INDICE + 1 /* Navegando um-a-um até < = 4, as quatro primeira posições */
		SET @VAR1 = @VAR1 - 1       /* subtraindo o algorítimo de 5 até 2 */
	END
 
	SET @VAR2 = 9
	WHILE ( @INDICE <= 12 )
	BEGIN
		SET @SOMA = @SOMA + CONVERT(INT, SUBSTRING(@CNPJ, @INDICE, 1)) * @VAR2
		SET @INDICE = @INDICE + 1
		SET @VAR2 = @VAR2 - 1            
	END

	SET @DIG1 = ( @SOMA % 11 )

   /* SE O RESTO DA DIVISÃO FOR < 2, O DIGITO = 0 */
	IF @DIG1 < 2
		SET @DIG1 = 0;
	ELSE /* SE O RESTO DA DIVISÃO NÃO FOR < 2*/
		SET @DIG1 = 11 - ( @SOMA % 11 );

	SET @INDICE = 1
	SET @SOMA = 0
	SET @VAR1 = 6 /* 2a Parte do Algorítimo começando de "6" */
	SET @RESULTADO = 0

	WHILE ( @INDICE <= 5 )
	BEGIN
		SET @SOMA = @SOMA + CONVERT(INT, SUBSTRING(@CNPJ, @INDICE, 1)) * @VAR1
		SET @INDICE = @INDICE + 1 /* Navegando um-a-um até < = 5, as quatro primeira posições */
		SET @VAR1 = @VAR1 - 1       /* subtraindo o algorítimo de 6 até 2 */
	END

	/* CÁLCULO DA 2ª PARTE DO ALGORÍTIOM 98765432 */
	SET @VAR2 = 9
	WHILE ( @INDICE <= 13 )
	BEGIN
		SET @SOMA = @SOMA + CONVERT(INT, SUBSTRING(@CNPJ, @INDICE, 1)) * @VAR2
		SET @INDICE = @INDICE + 1
		SET @VAR2 = @VAR2 - 1            
	END

	SET @DIG2 = ( @SOMA % 11 )

   /* SE O RESTO DA DIVISÃO FOR < 2, O DIGITO = 0 */
	IF @DIG2 < 2
		SET @DIG2 = 0;

	ELSE /* SE O RESTO DA DIVISÃO NÃO FOR < 2*/
		SET @DIG2 = 11 - ( @SOMA % 11 );

	IF ( @DIG1 = SUBSTRING(@CNPJ, LEN(@CNPJ) - 1, 1) ) AND ( @DIG2 = SUBSTRING(@CNPJ, LEN(@CNPJ), 1) )
		SET @RESULTADO = 1
	ELSE
		SET @RESULTADO = 0

	RETURN @RESULTADO
END

GO