INSERT INTO public.disease (name, type) VALUES ('Астигматизм', '')
INSERT INTO public.disease (name, type) VALUES ('Экзема', '');
INSERT INTO public.disease (name, type) VALUES ('Аневризма', 'Кардиология');
INSERT INTO public.disease (name, type) VALUES ('ВИЧ', 'Онкология');

INSERT INTO public.profession (name) VALUES ('Окулист')
INSERT INTO public.profession (name) VALUES ('Дерматолог');
INSERT INTO public.profession (name) VALUES ('Кардиолог');
INSERT INTO public.profession (name) VALUES ('Онколог');

INSERT INTO public.monitor (disease_id, profession_id) VALUES ((select id from public.disease where name = 'Астигматизм'), (select id from public.profession where name = 'Окулист'));
INSERT INTO public.monitor (disease_id, profession_id) VALUES ((select id from public.disease where name = 'Экзема'), (select id from public.profession where name = 'Дерматолог'));
INSERT INTO public.monitor (disease_id, profession_id) VALUES ((select id from public.disease where name = 'Аневризма'), (select id from public.profession where name = 'Кардиолог'));
INSERT INTO public.monitor (disease_id, profession_id) VALUES ((select id from public.disease where name = 'ВИЧ'), (select id from public.profession where name = 'Онколог'));

INSERT INTO public.doctor (birthdate, certificate, gender, name, phone, startwork, profession_id) VALUES ('1993-07-10', '560539 8759284', 'М', 'Курочкин Владислав Петрович', '65-54-90', '2024-06-09', (select id from public.profession where name = 'Окулист'));
INSERT INTO public.doctor (birthdate, certificate, gender, name, phone, startwork, profession_id) VALUES ('1991-05-11', '750739 7758281', 'М', 'Фидунов Александр Владимирович', '70-54-03', '2023-07-04', (select id from public.profession where name = 'Дерматолог'));
INSERT INTO public.doctor (birthdate, certificate, gender, name, phone, startwork, profession_id) VALUES ('1983-01-01', '552539 9759472', 'Ж', 'Кропоткина Галина Андреевна', '33-56-90', '2012-06-08', (select id from public.profession where name = 'Кардиолог'));
INSERT INTO public.doctor (birthdate, certificate, gender, name, phone, startwork, profession_id) VALUES ('1977-09-05', '551439 8759284', 'Ж', 'Гигантова Наталья Михайловна', '27-89-11', '2015-01-06', (select id from public.profession where name = 'Онколог'));

INSERT INTO public.patient (birthdate, gender, name, phone, disease_id, doctor_id, snils) VALUES ('1993-07-10', 'М', 'Летов Павел Владиславович', '64-53-90', (select id from public.disease where name = 'Астигматизм'), (select id from public.doctor where name = 'Курочкин Владислав Петрович'), '0102394914');
INSERT INTO public.patient (birthdate, gender, name, phone, disease_id, doctor_id, snils) VALUES ('1991-05-11', 'Ж', 'Королева Анастасия Георгиевна', '60-54-03', (select id from public.disease where name = 'Экзема'), (select id from public.doctor where name = 'Фидунов Александр Владимирович'), '1103394914');
INSERT INTO public.patient (birthdate, gender, name, phone, disease_id, doctor_id, snils) VALUES ('1983-01-01', 'М', 'Греков Максим Андреевич', '34-56-91', (select id from public.disease where name = 'Аневризма'), (select id from public.doctor where name = 'Кропоткина Галина Андреевна'), '9102394914');
INSERT INTO public.patient (birthdate, gender, name, phone, disease_id, doctor_id, snils) VALUES ('1977-09-05', 'Ж', 'Лучик Светлана Александровна', '28-89-12', (select id from public.disease where name = 'ВИЧ'), (select id from public.doctor where name = 'Гигантова Наталья Михайловна'), '8102394914');
INSERT INTO public.patient (birthdate, gender, name, phone, disease_id, doctor_id, snils) VALUES ('1977-09-05', 'М', 'Пользователь Для Удаления', '28-89-12', (select id from public.disease where name = 'ВИЧ'), (select id from public.doctor where name = 'Гигантова Наталья Михайловна'), '8102394914');
INSERT INTO public.patient (birthdate, gender, name, phone, disease_id, doctor_id, snils) VALUES ('1977-09-05', 'М', 'Пользователь Для Обновления', '28-89-12', (select id from public.disease where name = 'ВИЧ'), (select id from public.doctor where name = 'Гигантова Наталья Михайловна'), '8102394914');