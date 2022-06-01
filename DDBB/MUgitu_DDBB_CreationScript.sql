drop database if exists mugitu;
create database mugitu;
use mugitu;

create table bici(
	bici_id bigint,
	modelo nvarchar(128),
	electrica boolean,
	SOC int check ( SOC <= 100 && SOC >= 0 ),
    estado boolean
);

create table estacion(
	estacion_id bigint,
	nombre nvarchar(128),
	latitud double,
	longitud double,
	plazas int,
	activa boolean default true,
    ia boolean default false
);

create table tipo_usuario(
    tipo_user_id int,
    descripcion nvarchar(18)
);

create table usuario(
	user_id bigint,
	nombre nvarchar(128),
	apellidos nvarchar(128),
	correo nvarchar(128),
	telefono nvarchar(16),
	DNI nvarchar(16),
	sexo int,
	password nvarchar(128),
	fecha_nacimiento date,
	tipo_usuario_id int,
	verificado boolean
);

create table notificacion_averia(
	notificacion_id bigint,
	bici_id bigint,
	user_id bigint,
	fecha datetime,
	tipo_averia int,
    nueva boolean default true,
    resuelta boolean default false
);

create table tipo_averia(
    tipo_averia_id int,
    descripcion nvarchar(64)
);

create table averia(
    averia_id bigint,
    fecha_inicio datetime,
    fecha_fin datetime,
    bici_id bigint,
    tipo_averia int
);

create table estacionar(
    estacionar_id bigint,
    estacion_id bigint,
    bici_id bigint(128),
    fecha_inicio datetime,
    fecha_fin datetime
);

create table evento(
    evento_id bigint,
    bici_id bigint,
    estado boolean,
    fecha datetime
);

create table utilizacion(
	utiliza_id bigint,
	bici_id bigint,
	user_id bigint,
	fecha_inicio datetime,
	fecha_fin datetime
);

#Create primary key
ALTER TABLE tipo_usuario ADD CONSTRAINT pk_tipoUsuario PRIMARY KEY (tipo_user_id);
ALTER TABLE usuario ADD CONSTRAINT pk_user PRIMARY KEY (user_id);
ALTER TABLE bici ADD CONSTRAINT pk_bici PRIMARY KEY (bici_id);
ALTER TABLE estacion ADD CONSTRAINT pk_estacion PRIMARY KEY (estacion_id);
ALTER TABLE estacionar ADD CONSTRAINT pk_estacionar PRIMARY KEY (estacionar_id);
ALTER TABLE notificacion_averia ADD CONSTRAINT pk_notificacion PRIMARY KEY (notificacion_id);
ALTER TABLE tipo_averia ADD CONSTRAINT pk_tipoAveria PRIMARY KEY (tipo_averia_id);
ALTER TABLE averia ADD CONSTRAINT pk_averia PRIMARY KEY (averia_id);
ALTER TABLE evento ADD CONSTRAINT pk_evento PRIMARY KEY (evento_id);
ALTER TABLE utilizacion ADD CONSTRAINT pk_utilizacion PRIMARY KEY (utiliza_id);

#Set Auto Increments
ALTER TABLE usuario MODIFY COLUMN user_id BIGINT auto_increment;
ALTER TABLE bici MODIFY COLUMN bici_id BIGINT auto_increment;
ALTER TABLE estacion MODIFY COLUMN estacion_id BIGINT auto_increment;
ALTER TABLE estacionar MODIFY COLUMN estacionar_id BIGINT auto_increment;
ALTER TABLE notificacion_averia MODIFY COLUMN notificacion_id BIGINT auto_increment;
ALTER TABLE averia MODIFY averia_id BIGINT auto_increment;
ALTER TABLE evento MODIFY evento_id BIGINT auto_increment;
ALTER TABLE utilizacion MODIFY utiliza_id BIGINT auto_increment;

#Create foreign key
ALTER TABLE usuario ADD CONSTRAINT fk_usuario_tipoUsuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(tipo_user_id);
ALTER TABLE estacionar ADD CONSTRAINT fk_estacionar_estacion FOREIGN KEY (estacion_id) REFERENCES estacion(estacion_id);
ALTER TABLE estacionar ADD CONSTRAINT fk_estacionar_bici FOREIGN KEY (bici_id) REFERENCES bici(bici_id);
ALTER TABLE notificacion_averia ADD CONSTRAINT fk_notificacion_user FOREIGN KEY (user_id) REFERENCES usuario(user_id);
ALTER TABLE notificacion_averia ADD CONSTRAINT fk_notificacion_bici FOREIGN KEY (bici_id) REFERENCES bici(bici_id);
ALTER TABLE averia ADD CONSTRAINT fk_averia_bici FOREIGN KEY (bici_id) REFERENCES bici (bici_id);
ALTER TABLE evento ADD CONSTRAINT fk_evento_bici FOREIGN KEY (bici_id) REFERENCES bici (bici_id);
ALTER TABLE utilizacion ADD CONSTRAINT fk_utiliza_user FOREIGN KEY (user_id) REFERENCES usuario (user_id);
ALTER TABLE utilizacion ADD CONSTRAINT fk_utiliza_bici FOREIGN KEY (bici_id) REFERENCES bici (bici_id);


#Create User for web app
CREATE USER 'mugituAdmin'@'%' IDENTIFIED BY 'Mugitu@2022';
GRANT ALL PRIVILEGES ON mugitu.* TO 'mugituAdmin'@'%';
FLUSH PRIVILEGES;
