USE MangoSLR;

CREATE TABLE Detalle (
    idDetalle       INT PRIMARY KEY NOT NULL IDENTITY,
    idProducto      INT NOT NULL,
    idFactura       INT NOT NULL,
    cantidad        INT,
    precioUnidad    FLOAT,
    total           FLOAT
);

ALTER TABLE Detalle ADD CHECK (cantidad >= 1);
ALTER TABLE Detalle ADD CHECK (precioUnidad >= 0);
ALTER TABLE Detalle ADD CHECK (total >= 0);

CREATE TABLE Factura (
    idFactura       INT PRIMARY KEY NOT NULL IDENTITY,
    idFarmacia      INT NOT NULL,
    idUsuario       INT NOT NULL,
    total           FLOAT,
    fecha           DATETIME
);

ALTER TABLE Factura ADD CHECK (total >= 0);

CREATE TABLE Farmacia(
    idFarmacia      INT PRIMARY KEY NOT NULL IDENTITY,
    nombre          VARCHAR(50),
    direccion       VARCHAR(512),
    telefono        VARCHAR(20),
    imageUrl        VARCHAR(256)
);

CREATE TABLE Persona(
    idPersona       INT PRIMARY KEY NOT NULL IDENTITY,
    nombre          VARCHAR(30),
    apellido        VARCHAR(30),
    sexo            CHAR,
    tipoSangre      VARCHAR(5),
    fechaNacimiento DATE,
    direccion       VARCHAR(512),
    latDireccion    FLOAT,
    lonDireccion    FLOAT,
    telefono        VARCHAR(20),
    cedula          VARCHAR(15) UNIQUE, 
    celular         varchar(20)
);

ALTER TABLE Persona ADD CHECK (sexo = 'F' OR sexo = 'M');
ALTER TABLE Persona ADD CHECK (tipoSangre IN ('O+', 'O-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O', 'A', 'B', 'AB'));

CREATE TABLE Producto(
    idProducto      INT PRIMARY KEY NOT NULL,
    nombre          VARCHAR(32),
    descripcion     VARCHAR(512),
    cantidad        INT,
    costo           FLOAT,
    imageUrl        VARCHAR(256)
);

ALTER TABLE Producto ADD CHECK (cantidad >= 0);
ALTER TABLE Producto ADD CHECK (costo >= 0);

CREATE TABLE Usuario(
    idUsuario       INT PRIMARY KEY NOT NULL,
    idPersona       INT NOT NULL,
    idFarmacia      INT NOT NULL,
    tipoUsuario     VARCHAR(20),
    nombreUsuario   VARCHAR(20),
    clave           VARCHAR(20),
    correo          VARCHAR(150),
    imageUrl        VARCHAR(256)
);

-- Constraint (Foreign Keys)
-- Para los detalles
ALTER TABLE Detalle ADD CONSTRAINT FK_Producto
    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto);

ALTER TABLE Detalle ADD CONSTRAINT FK_Factura
    FOREIGN KEY (idFactura) REFERENCES Factura(idFactura);

-- Para la factura...
ALTER TABLE Factura ADD CONSTRAINT FK_Usuario
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario);

ALTER TABLE Factura ADD CONSTRAINT FK_Farmacia
    FOREIGN KEY (idFarmacia) REFERENCES Farmacia(idFarmacia);

-- Para los usuarios...
ALTER TABLE Usuario ADD CONSTRAINT FK_Persona
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona);

ALTER TABLE Usuario ADD CONSTRAINT FK_FarmaciaUsuario
    FOREIGN KEY (idFarmacia) REFERENCES Farmacia(idFarmacia);
