-- offer
INSERT INTO offer (offer_id, offer_name) VALUES (1, 'Smart');
INSERT INTO offer (offer_id, offer_name) VALUES (2, 'Тёплый приём');
INSERT INTO offer (offer_id, offer_name) VALUES (3, 'Сверхчёрный');
INSERT INTO offer (offer_id, offer_name) VALUES (4, 'Чёрный');
INSERT INTO offer (offer_id, offer_name) VALUES (5, 'Переходи на ноль');
INSERT INTO offer (offer_id, offer_name) VALUES (6, 'Всё включено XS');

-- offer_attributes
INSERT INTO offer_attributes (attr_id, attr_name, description) VALUES (1, 'Абонентская плата', 'Плата за предоставленные в пакете услуги за месяц');
INSERT INTO offer_attributes (attr_id, attr_name, description) VALUES (2, 'Мобильный интернет', 'Интернет-трафик');
INSERT INTO offer_attributes (attr_id, attr_name, description) VALUES (3, 'Количство бесплатных sms', 'Количество sms по области');
INSERT INTO offer_attributes (attr_id, attr_name, description) VALUES (4, 'Спутниковая связь', 'Стоимость вызова на телефоны операторов спутниковой связи');
INSERT INTO offer_attributes (attr_id, attr_name, description) VALUES (5, 'Исходящие звонки по области', 'Число бесплатных минут');
INSERT INTO offer_attributes (attr_id, attr_name, description) VALUES (6, 'Исходящие звонки по России', 'Число бесплатных минут');

-- params
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (1, 1, '300');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (2, 1, '0');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (3, 1, '490');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (4, 1, '90');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (5, 1, '0');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (6, 1, '199');

INSERT INTO params (offer_id, attr_id, attr_value) VALUES (1, 2, '5 ГБ');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (2, 2, '100 МБ');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (3, 2, '10 ГБ');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (4, 2, '500 МБ');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (5, 2, '100 МБ');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (6, 2, '3 ГБ');

INSERT INTO params (offer_id, attr_id, attr_value) VALUES (1, 3, '700');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (2, 3, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (3, 3, '1000');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (4, 3, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (5, 3, '100 МБ');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (6, 3, '200');

INSERT INTO params (offer_id, attr_id, attr_value) VALUES (3, 4, '240');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (4, 4, '240');

INSERT INTO params (offer_id, attr_id, attr_value) VALUES (1, 5, '700');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (2, 5, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (3, 5, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (4, 5, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (5, 5, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (6, 5, '200');

INSERT INTO params (offer_id, attr_id, attr_value) VALUES (1, 6, '700');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (2, 6, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (3, 6, '1000');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (4, 6, '100');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (5, 6, 'безлимит');
INSERT INTO params (offer_id, attr_id, attr_value) VALUES (6, 6, 'безлимит');

-- usr
INSERT INTO usr (usr_id, usr_name) VALUES (1, 'Smith'   );
INSERT INTO usr (usr_id, usr_name) VALUES (2, 'Тюмиков' );
INSERT INTO usr (usr_id, usr_name) VALUES (3, 'Ward'    );
INSERT INTO usr (usr_id, usr_name) VALUES (4, 'Калинина');
INSERT INTO usr (usr_id, usr_name) VALUES (5, 'Радаев'  );
INSERT INTO usr (usr_id, usr_name) VALUES (6, 'Blake'   );
INSERT INTO usr (usr_id, usr_name) VALUES (7, 'Захарова');

--user_offer
INSERT INTO user_offer (usr_id, offer_id) VALUES (1, 4);
INSERT INTO user_offer (usr_id, offer_id) VALUES (2, 3);
INSERT INTO user_offer (usr_id, offer_id) VALUES (3, 1);
INSERT INTO user_offer (usr_id, offer_id) VALUES (4, 4);
INSERT INTO user_offer (usr_id, offer_id) VALUES (5, 2);
INSERT INTO user_offer (usr_id, offer_id) VALUES (6, 5);
INSERT INTO user_offer (usr_id, offer_id) VALUES (7, 6);
