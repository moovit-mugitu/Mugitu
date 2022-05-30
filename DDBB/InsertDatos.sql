USE mugitu;

#User types
INSERT INTO tipo_usuario (tipo_user_id, descripcion)
VALUES (1,'ADMIN'),(2,'USER'),(3,'WORKER');

#Usuarios básicos, ADMIN y USER con la password igual que el nombre
INSERT INTO usuario (nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
('admin', 'admin', 'admin@admin','00000000A', '$2a$10$.tZglXmO7tFDgz5jRVJpGO/YZXU8gKx3aPIcSLVz3s4ETa/WcDlYi', 1, true);
INSERT INTO usuario (nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
('user', 'user', 'user@user','11111111B', '$2a$10$m9Gr6Az8dDmGljI8agZRCOCqf2XBYWu9nyxgaTCuLek5DexdntkBm', 2, true);
INSERT INTO usuario (nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
('worker', 'worker', 'worker@worker', '22222222C', '$2a$10$vp7yzrts9c928pvTJg70LeaZbHMNSfXdNMuEzNxg.WtlyVmHNGXyC', 3, 1);

#Users de relleno
INSERT INTO usuario (user_id, nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
    (6, 'user6', 'user6', 'user6@user','11111116B', '$2a$10$m9Gr6Az8dDmGljI8agZRCOCqf2XBYWu9nyxgaTCuLek5DexdntkBm', 2, true);
INSERT INTO usuario (user_id, nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
    (7, 'user7', 'user7', 'user7@user','11111117B', '$2a$10$m9Gr6Az8dDmGljI8agZRCOCqf2XBYWu9nyxgaTCuLek5DexdntkBm', 2, true);
INSERT INTO usuario (user_id, nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
    (4, 'user4', 'user4', 'user4@user','11111114B', '$2a$10$m9Gr6Az8dDmGljI8agZRCOCqf2XBYWu9nyxgaTCuLek5DexdntkBm', 2, true);
INSERT INTO usuario (user_id, nombre, apellidos, correo, DNI, password, tipo_usuario_id, verificado) VALUES
    (5, 'user5', 'user5', 'user5@user','11111115B', '$2a$10$m9Gr6Az8dDmGljI8agZRCOCqf2XBYWu9nyxgaTCuLek5DexdntkBm', 2, true);

#Estacion
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (1, 'Puerta del Sol A', 404172137, -37018341, 30, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (2, 'Puerta del Sol B', 404172137, -37018341, 30, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (3, 'Miguel Moya', 404205886, -37058415, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (4, 'Plaza Conde Suchil', 404302937, -37069171, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (5, 'Malasau00f1a', 404285524, -37025875, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (6, 'Fuencarral', 4042852, -370205, 27, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (7, 'Colegio Arquitectos', 40424148, -3698447, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (8, 'Hortaleza', 404251906, -36977715, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (9, 'Alonso Martu00ednez', 404278682, -36954403, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (10, 'Plaza de San Miguel', 404156057, -37095084, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (11, 'Marquu00e9s de la Ensenada', 4042533, -369214, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (12, 'San Andru00e9s', 404269483, -37035918, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (13, 'San Hermenegildo', 404284246, -37061931, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (14, 'Conde Duque', 404273264, -37104417, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (15, 'Ventura Rodru00edguez', 404260957, -3713479, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (16, 'San Vicente Ferrer', 404262383, -37074453, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (17, 'San Bernardo', 404230721, -37075065, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (18, 'Carlos Cambronero', 404232649, -37038312, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (19, 'Plaza de Pedro Zerolo', 404207773, -36996502, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (20, 'Prim', 404218616, -36954983, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (21, 'Banco de Espau00f1a A', 404192342, -36954615, 30, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (25, 'Jacometrezo', 404200783, -37065376, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (26, 'Santo Domingo', 404202834, -37081246, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (27, 'Palacio de Oriente', 40417908, -3710692, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (28, 'Plaza de Celenque A', 404173703, -37057791, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (29, 'Plaza de Celenque B', 404172781, -37063837, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (30, 'Plaza de las Salesas', 40423855, -3694475, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (31, 'Huertas', 404132798, -36956178, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (32, 'Sevilla', 404181518, -36984368, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (33, 'Marquu00e9s de Cubas', 404162619, -36957355, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (34, 'San Quintu00edn', 404192095, -3711504, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (35, 'Calle Mayor', 404163638, -37068969, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (36, 'Plaza de la Provincia', 404150099, -37061032, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (37, 'Carretas', 404165039, -37030833, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (38, 'Jacinto Benavente', 404146755, -37036825, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (39, 'Plaza del Cordu00f3n', 404141931, -37103285, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (40, 'Plaza de Ramales', 404168, -371183, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (41, 'Plaza San Francisco', 404108442, -37144964, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (42, 'Plaza de los Carros', 404114041, -37114762, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (43, 'Plaza de la Cebada', 404112744, -37088337, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (44, 'Conde de Romanones', 404138846, -37049407, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (45, 'Antu00f3n Martu00edn', 404122047, -36991147, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (46, 'Santa Isabel', 404107085, -36982318, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (47, 'Jesu00fas y Maru00eda', 404101564, -37025024, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (48, 'Plaza de Nelson Mandela', 404097617, -37040666, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (49, 'Puerta de Toledo', 404070358, -37110513, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (50, 'Ribera de Curtidores', 404053153, -37071259, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (51, 'Embajadores 1', 404047851, -37028265, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (52, 'Embajadores 2', 404056107, -37022591, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (53, 'Casa Encendida', 404060941, -36992759, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (54, 'Museo Reina Sofu00eda', 404083684, -36933463, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (55, 'Ronda de Atocha', 404075606, -36935205, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (56, 'Plaza de Santa Ana', 404144226, -37007164, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (57, 'Plaza de Lavapiu00e9s', 404083061, -37007111, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (58, 'Barcelu00f3', 404266828, -3700423, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (59, 'Plaza de San Ildefonso', 404239757, -37020842, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (60, 'Plaza del Carmen ', 404184192, -37032414, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (61, 'Santa Cruz del Marcenado', 404295658, -37126299, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (62, 'Augusto Figueroa', 404222862, -3697895, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (63, 'Plaza de Juan Pujol', 404255495, -37043418, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (64, 'Plaza de la Independencia', 40419752, -3688398, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (65, 'Narvu00e1ez', 404213983, -36752045, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (66, 'O''Donnell', 404213148, -36724968, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (67, 'Ibiza', 404179237, -36708959, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (69, 'Antonio Maura', 404166834, -36894193, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (71, 'Almadu00e9n', 404108472, -3693225, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (72, 'Espalter', 404128372, -36912023, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (73, 'Puerta del u00c1ngel Cau00eddo', 40409808, -3688822, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (74, 'Puerta del Doce de Octubre', 404153053, -36779232, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (75, 'Doce de Octubre', 404159569, -36738865, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (76, 'Sainz de Baranda', 404157413, -36691838, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (77, 'Plaza de los Astros', 404114475, -36689089, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (78, 'Puerta del Pacu00edfico ', 404117627, -36766813, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (79, 'Menu00e9ndez Pelayo', 404082805, -36784838, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (80, 'Puerta de Mariano de Cavia', 404071304, -36751352, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (81, 'Conde de Casal', 4040635, -3670422, 22, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (82, 'Pedro Bosch', 40400789, -36744, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (83, 'Puerta de Granada', 404051451, -36803874, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (84, 'Atocha A', 404075685, -36902255, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (85, 'Atocha B', 404074902, -36901234, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (86, 'Cuesta de Moyano', 40409297, -3691987, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (87, 'Niu00f1o Jesu00fas', 404084556, -36697526, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (88, 'Pu00edo Baroja', 404132995, -36745323, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (89, 'Valderribas', 404032501, -36726019, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (90, 'Puerta de Madrid', 40421501, -3680008, 27, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (91, 'Cibeles', 404186516, -36933498, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (92, 'Ayala', 40427736, -36832566, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (93, 'Embajada de Italia', 404313576, -36838303, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (94, 'Conde Peu00f1alver', 404272582, -36752024, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (95, 'General Pardiu00f1as', 404250361, -36837876, 30, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (96, 'Pru00edncipe de Vergara', 40426134, -36787441, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (97, 'Claudio Coello', 404262945, -36865463, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (98, 'Plaza de Colu00f3n', 404257046, -36893698, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (99, 'Biblioteca Nacional', 404232153, -3690756, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (100, 'Villanueva', 404226584, -36870548, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (101, 'Castellu00f3', 40422064, -36821793, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (102, 'Alcalu00e1', 404222969, -36805189, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (103, 'Plaza de Felipe II', 404239447, -36758383, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (104, 'Alcu00e1ntara', 404261851, -36738714, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (105, 'Palacio de Deportes', 4042478, -367384, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (106, 'Jorge Juan', 404231526, -36691524, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (107, 'Velu00e1zquez', 404211802, -36840229, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (108, 'Ortega y Gasset', 4043037, -368653, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (109, 'Castellana', 4042677, -368958, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (110, 'Serrano', 4042699, -368745, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (111, 'Colu00f3n A', 404251002, -36877227, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (112, 'Colu00f3n B', 40424963, -3687745, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (113, 'Columela', 404215246, -36884369, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (114, 'Mu00e1rtires Concepcionistas', 404273005, -36706024, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (115, 'Marquu00e9s de Salamanca', 404300481, -36816402, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (116, 'Moncloa', 404347895, -37200845, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (117, 'Arcipreste de Hita A', 404337322, -37175435, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (118, 'Arcipreste de Hita B', 404341006, -37179687, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (119, 'Paseo de Moret', 404325991, -37246532, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (120, 'Pintor Rosales', 40427657, -37205129, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (121, 'Quintana', 404277456, -37174158, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (122, 'Ferraz', 404253944, -37170448, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (123, 'Plaza de Espau00f1a A', 4042402, -3711603, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (124, 'Plaza de Espau00f1a B', 4042412, -3711703, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (125, 'Altamirano', 404309797, -37188898, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (126, 'Juan Martu00edn', 40400781, -36882407, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (127, 'Mu00e9ndez u00c1lvaro', 404013216, -36863218, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (128, 'Palos de la Frontera', 404032208, -36944768, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (129, 'Santa Maru00eda de la Cabeza', 404017926, -36987665, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (130, 'Santa Engracia 14', 404291992, -36967169, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (131, 'Guzmu00e1n el Bueno', 404306458, -37133412, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (132, 'Paseo de la Florida', 404219665, -37224983, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (133, 'Metro Piru00e1mides', 404034076, -37108108, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (134, 'Paseo de la Esperanza', 404035988, -37064516, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (135, 'Entrada Matadero', 403928821, -36975708, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (136, 'Paseo de las Delicias', 403972616, -36945025, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (137, 'Castellana 164', 404591366, -36894151, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (138, 'Alberto Alcocer', 404585318, -3684715, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (139, 'San Germu00e1n', 404572824, -37009675, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (140, 'Sor u00c1ngela de la Cruz', 404592351, -3691533, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (141, 'Orense 36', 404548456, -36946218, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (142, 'General Peru00f3n 1', 404527164, -36990077, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (143, 'General Peru00f3n con Poeta Joan Maragall', 404522454, -3693085, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (144, 'Serrano 210', 404510188, -36817962, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (145, 'Orense 12', 404489101, -36952943, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (146, 'Paseo de la Habana 42', 404498613, -36881689, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (147, 'Castellana frente a Hermanos Pinzu00f3n', 404488924, -36905604, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (148, 'Doctor Arce 45', 404483269, -36797296, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (149, 'Glorieta de los Cuatro Caminos', 404463667, -37036675, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (150, 'Raimundo Fernu00e1ndez', 40447125, -37001669, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (151, 'Castellana 106', 404453307, -3690861, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (152, 'Plaza de la Repu00fablica Argentina', 40445411, -36853312, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (153, 'Agustu00edn de Betancourt', 404440297, -36956047, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (154, 'Paseo de la Castellana con Raimundo Fernu00e1ndez', 404457414, -36917932, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (155, 'Maru00eda Francisca 1', 404442258, -36787169, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (156, 'Bravo Murillo 44', 404418402, -37040344, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (157, 'Santa Engracia 127', 4044156, -370164, 20, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (158, 'Plaza de San Juan de la Cruz', 404415974, -36927821, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (159, 'Josu00e9 Gutiu00e9rrez Abascal ', 404396792, -36907784, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (160, 'Cea Bermu00fadez', 40438994, -37154329, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (161, 'Josu00e9 Abascal', 404383865, -36982353, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (162, 'Velu00e1zquez 130', 404385127, -36831578, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (163, 'General u00c1lvarez de Castro', 404344731, -37015686, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (164, 'General Martu00ednez Campos ', 40435285, -36948626, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (165, 'Pu00ba Castellana - Glorieta de Emilio Castelar', 404355143, -36892368, 12, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (166, 'Diego de Leu00f3n 52', 404345973, -3678492, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (167, 'Castellana 42', 404334087, -36879154, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (168, 'Fernando el Catu00f3lico', 404338516, -3708439, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (169, 'Manuel Silvela', 404309524, -36993465, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (170, 'Juan Bravo 50', 404323655, -36758555, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (171, 'Ortega y Gasset 87', 40429887, -36712823, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (172, 'Colombia', 404572466, -36763439, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (173, 'Paseo de la Habana 63', 404543852, -36835926, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (174, 'Segovia 26', 404138333, -37135833, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (175, 'Segovia 45', 40413736, -3717487, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (176, 'Batalla del Salado', 404044722, -36959166, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (177, 'Piru00e1mides', 404016111, -37138055, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (178, 'Paseo de la Esperanza', 40401, -37043611, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (179, 'Embajadores-Cu00e1ceres', 4039975, -36983888, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (180, 'Delicias', 404008888, -36935833, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (181, 'Puente Praga', 403981798, -37023918, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (182, 'Madrid Ru00edo-Yeseru00edas', 403974444, -37065277, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (183, 'Jaime el Conquistador', 403962222, -36983055, 21, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (184, 'Beata', 4039425, -36937222, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (185, 'Legazpi', 403914722, -36941944, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (186, 'Junta Municipal Retiro', 404029202, -36788343, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (187, 'Puente de Vallecas', 403979722, -366925, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (188, 'Mu00e9ndez u00c1lvaro', 403941388, -36758888, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (189, 'Retiro-Ibiza', 4041825, -36766111, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (190, 'Parque Roma', 404186666, -36657777, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (191, 'Doctor Esquerdo-Hermosilla', 40425635, -3669339, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (192, 'Marquu00e9s de Zafra', 40426, -36653055, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (193, 'Quinta Fuente del Berro', 404250833, -36615277, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (194, 'General Pardiu00f1as', 404290555, -36782777, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (195, 'Alcalu00e1-Ventas', 404301666, -36638888, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (196, 'Puente de Ventas', 404311944, -36590555, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (197, 'Avenida Donostiarra', 404351256, -36554918, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (198, 'Velu00e1zquez-Diego de Leu00f3n', 404343333, -36835833, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (199, 'Diego de leu00f3n', 404346388, -3674, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (200, 'Avenida de los Toreros', 404318611, -36714166, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (201, 'Ventas', 404324722, -36655555, 27, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (202, 'Avenida de Amu00e9rica 2', 4043725, -36772222, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (203, 'Avenida de Amu00e9rica 1', 404392222, -36754444, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (204, 'Prosperidad', 404441388, -36751388, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (205, 'Parque Berlu00edn', 404515833, -36780555, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (206, 'Rubu00e9n Daru00edo', 404331111, -36915555, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (207, 'Fernando el Catu00f3lico', 404343611, -37149166, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (208, 'Quevedo', 404336944, -37046666, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (209, 'Canal', 404386944, -37038333, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (210, 'Parque Santander', 404411388, -37080833, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (211, 'San Francisco de Sales', 4044175, -37144722, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (212, 'Cuatro Caminos 2', 404476666, -37041666, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (213, 'Estrecho', 404535, -37032777, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (214, 'Metro Tetuu00e1n', 404607502, -36989925, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (215, 'Remonta', 404630277, -36973333, 18, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (216, 'Plaza de Castilla', 404663611, -36886388, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (217, 'Plaza de Castilla 2', 404656779, -36887722, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (218, 'Tres Cruces', 40419674, -37026735, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (219, 'Desengau00f1o', 4042059, -370239, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (220, 'Marquu00e9s de Vadillo', 40398247, -3716591, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (221, 'Glorieta de Cu00e1diz', 4038895, -370017, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (222, 'Condesa de Venadito', 4044315, -365741, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (223, 'Gutierrez de Cetina', 4044364, -364917, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (224, 'Puerta del u00c1ngel', 40413764, -3728318, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (225, 'Pedro Rico', 40481086, -3688463, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (226, 'Camino Vinateros', 4041053, -365779, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (227, 'Marroquina', 40408264, -3646697, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (228, 'Plaza del Encuentro', 4040617, -365122, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (229, 'Pavones', 40400368, -3634587, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (230, 'Antonio Lu00f3pez', 403917871, -37026789, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (231, 'Ermita Santo', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (232, 'Caramuel', 4, -3, 27, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (233, 'Doctor Lozano', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (234, 'Sierra Toledana', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (235, 'San Delfu00edn', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (236, 'Concordia', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (237, 'C.D. Concepciu00f3n', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (238, 'Carlota Ou2019Neill', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (239, 'Derechos Humanos', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (240, 'Josu00e9 Maru00eda Pereda', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (241, 'Marquu00e9s de Corbera 12', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (242, 'Marquu00e9s de Corbera 52', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (243, 'Embajadores 191', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (244, 'Paseo Imperial', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (245, 'Pablo Iglesias', 4, -3, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (246, 'San Germu00e1n', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (247, 'Francos Rodru00edguez', 4, -3, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (248, 'Avenida Brasilia', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (249, 'Camilo Josu00e9 Cela', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (250, 'Avenida Bruselas', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (251, 'Cartagena', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (252, 'Hospital Clu00ednico', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (253, 'Galileo', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (254, 'Santa Engracia 87', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (256, 'Torre Cepsa', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (257, 'Manuel Caldeiro', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (258, 'Serrano 113', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (259, 'Corazu00f3n de Maru00eda', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (260, 'Chamartu00edn', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (261, 'Doctor Fleming', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (262, 'Pu00edo XII', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (263, 'Lu00f3pez Pozas', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (264, 'Reina Victoria', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (265, 'INEF', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (266, 'Ciudad Universitaria 1', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (267, 'Ciudad Universitaria 2', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (268, 'Facultad Biologu00eda', 0, 0, 24, 1, 0);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (269, 'Facultad Derecho', 0, 0, 24, 1, 1);
INSERT INTO mugitu.estacion (estacion_id, nombre, latitud, longitud, plazas, activa, ia) VALUES (270, 'Zurbano', 0, 0, 24, 1, 0);



#Tipo de averias LEVE; MODERADA, GRABE
INSERT INTO tipo_averia (tipo_averia_id, descripcion) VALUES
(1, 'Averia leve (luz no funciona...)');
INSERT INTO tipo_averia (tipo_averia_id, descripcion) VALUES
(2, 'Averia moderada (fallo mecanico)');
INSERT INTO tipo_averia (tipo_averia_id, descripcion) VALUES
(3, 'Averia grave (bicicleta inutilizable)');

#Bicis
INSERT INTO  bici (bici_id, modelo, electrica, SOC, estado) VALUES
(1, 'BH 1', false, null, 1);
INSERT INTO  bici (bici_id, modelo, electrica, SOC, estado) VALUES
(2, 'BH 2', true, 100, 1);
INSERT INTO  bici (bici_id, modelo, electrica, SOC, estado) VALUES
(3, 'BH 3', false, null, 1);

#Averias
INSERT INTO mugitu.averia (averia_id, fecha_inicio, fecha_fin, bici_id, tipo_averia) VALUES (1, '2022-05-17 00:00:00', '2022-05-18 00:00:00', 1, 1);
INSERT INTO mugitu.averia (averia_id, fecha_inicio, fecha_fin, bici_id, tipo_averia) VALUES (2, '2022-05-18 00:00:00', '2022-05-19 00:00:00', 2, 2);
INSERT INTO mugitu.averia (averia_id, fecha_inicio, fecha_fin, bici_id, tipo_averia) VALUES (3, '2022-05-19 00:00:00', '2022-05-21 00:00:00', 3, 3);

#Estacionar
INSERT INTO mugitu.estacionar (estacionar_id, estacion_id, bici_id, fecha_inicio, fecha_fin) VALUES (1, 1, 1, '2022-05-18 10:51:08', null);

#Evento
INSERT INTO mugitu.evento (evento_id, bici_id, estado, fecha) VALUES (1, 2, 1, '2022-05-17 10:52:43');
INSERT INTO mugitu.evento (evento_id, bici_id, estado, fecha) VALUES (2, 2, 0, '2022-05-18 10:52:16');

#Utilizacion
INSERT INTO mugitu.utilizacion (utiliza_id, bici_id, user_id, fecha_inicio, fecha_fin) VALUES (1, 3, 2, '2022-05-18 09:53:31', null);
