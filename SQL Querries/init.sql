CREATE DATABASE MangoSLR;
go

USE MangoSLR;

CREATE LOGIN mango WITH PASSWORD='R+fPrT76sX&hp7zs?Saq6Q%K_RU8aMwn';
CREATE USER mango FOR LOGIN mango;
go

EXEC sp_addrolemember 'db_owner', 'mango'
go

SELECT * FROM dbo.productos;