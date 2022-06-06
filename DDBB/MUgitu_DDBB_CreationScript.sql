drop database if exists mugitu;
create database mugitu;
use mugitu;

create table bici
(
    bici_id   bigint auto_increment
        primary key,
    modelo    varchar(128) charset utf8 null,
    electrica tinyint(1)                null,
    SOC       int                       null,
    estado    tinyint(1)                null,
    check ((`SOC` <= 100) and (`SOC` >= 0))
);

create table estacion
(
    estacion_id bigint auto_increment
        primary key,
    nombre      varchar(128) charset utf8 null,
    latitud     double                    null,
    longitud    double                    null,
    plazas      int                       null,
    activa      tinyint(1) default 1      null,
    ia          tinyint(1) default 0      null
);

create table estacionar
(
    estacionar_id bigint auto_increment
        primary key,
    estacion_id   bigint   null,
    bici_id       bigint   null,
    fecha_inicio  datetime null,
    fecha_fin     datetime null,
    constraint fk_estacionar_bici
        foreign key (bici_id) references bici (bici_id),
    constraint fk_estacionar_estacion
        foreign key (estacion_id) references estacion (estacion_id)
);

create table evento
(
    evento_id bigint auto_increment
        primary key,
    bici_id   bigint     null,
    estado    tinyint(1) null,
    fecha     datetime   null,
    latitud   double     null,
    longitud  double     null,
    constraint fk_evento_bici
        foreign key (bici_id) references bici (bici_id)
);

create table tipo_averia
(
    tipo_averia_id int                      not null
        primary key,
    descripcion    varchar(64) charset utf8 null
);

create table averia
(
    averia_id    bigint auto_increment
        primary key,
    fecha_inicio datetime null,
    fecha_fin    datetime null,
    bici_id      bigint   null,
    tipo_averia  int      null,
    constraint FKlxesfdj91dde1jhd3asx2s9ef
        foreign key (tipo_averia) references tipo_averia (tipo_averia_id),
    constraint fk_averia_bici
        foreign key (bici_id) references bici (bici_id)
);

create table tipo_usuario
(
    tipo_user_id int                      not null
        primary key,
    descripcion  varchar(18) charset utf8 null
);

create table usuario
(
    user_id          bigint auto_increment
        primary key,
    nombre           varchar(128) charset utf8 null,
    apellidos        varchar(128) charset utf8 null,
    correo           varchar(128) charset utf8 null,
    telefono         varchar(16) charset utf8  null,
    DNI              varchar(16) charset utf8  null,
    sexo             int                       null,
    password         varchar(128) charset utf8 null,
    fecha_nacimiento date                      null,
    tipo_usuario_id  int                       null,
    verificado       tinyint(1)                null,
    constraint fk_usuario_tipoUsuario
        foreign key (tipo_usuario_id) references tipo_usuario (tipo_user_id)
);

create table notificacion_averia
(
    notificacion_id bigint auto_increment
        primary key,
    bici_id         bigint               null,
    user_id         bigint               null,
    fecha           datetime             null,
    tipo_averia     int                  null,
    nueva           tinyint(1) default 1 null,
    resuelta        tinyint(1) default 0 null,
    mensaje         varchar(128)         null,
    constraint fk_notificacion_bici
        foreign key (bici_id) references bici (bici_id),
    constraint fk_notificacion_tipoAveria
        foreign key (tipo_averia) references tipo_averia (tipo_averia_id),
    constraint fk_notificacion_user
        foreign key (user_id) references usuario (user_id)
);

create table utilizacion
(
    utiliza_id   bigint auto_increment
        primary key,
    bici_id      bigint   null,
    user_id      bigint   null,
    fecha_inicio datetime null,
    fecha_fin    datetime null,
    constraint fk_utiliza_bici
        foreign key (bici_id) references bici (bici_id),
    constraint fk_utiliza_user
        foreign key (user_id) references usuario (user_id)
);



#Create User for web app
CREATE USER 'mugituAdmin'@'%' IDENTIFIED BY 'Mugitu@2022';
GRANT ALL PRIVILEGES ON mugitu.* TO 'mugituAdmin'@'%';
FLUSH PRIVILEGES;