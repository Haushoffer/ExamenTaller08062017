INSERT INTO role(id, name) VALUES (1,"USER");
INSERT INTO role(id, name) VALUES (2,"ADMIN");
INSERT INTO user(id, age, blocked, gender, heigth, password, username, weigth, last_name, name) VALUES (1, 0, 0,1, 0.00, "$2a$10$3Vm1lU8TJnB/gISKaj.xuehBfznX3CmXxef/qB1tVh4kZ5.D6OLY6", "Admin@Cato.com", 0.00, "One", "Administrator");
--username:Admin@Cato.com password : admin123
INSERT INTO user_role(user_id, role_id) VALUES (1,2);
INSERT INTO user_role(user_id, role_id) VALUES (1,1);
INSERT INTO band (id,modelo,serial,user_id) VALUES (1,"Garmin VivoSmart HR+","gg123alv",1)
INSERT INTO band (id,modelo,serial) VALUES (2,"Adidas Pro","123abc")
INSERT INTO band (id,modelo,serial) VALUES (3,"Garmin VivoSmart HR+","qwerty123")
--poner mas modelos y seriales
--no se si agregar location, steps y HR de ejemplo
--los datos de todas las tablas se borraran y se insertaran estos datos
--para activar el importe, cambiar el 'spring.jpa.hibernate.ddl-auto=update' a 'spring.jpa.hibernate.ddl-auto=create'
