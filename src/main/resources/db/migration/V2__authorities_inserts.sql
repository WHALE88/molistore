INSERT INTO public.authority
(id, "role")
VALUES(1, 'ROLE_CUSTOMER');

INSERT INTO public.authority
(id, "role")
VALUES(2, 'ROLE_SEO');

INSERT INTO public.authority
(id, "role")
VALUES(3, 'ROLE_ADMINISTRATOR');

INSERT INTO public.authority
(id, "role")
VALUES(4, 'ROLE_SUPERVISOR');

INSERT INTO public.plain_profiles
(id, account, birth_date, firstname, gender, "language", lastname)
VALUES(1, '0817990045610039', '1899-05-09', 'Yulia', 'FEMALE', 'UA', 'Test');

INSERT INTO public.users
(id, active, create_date_time, delete_date_time, email, "password", plain_profile_id)
VALUES(1, true, '2020-03-10', NULL, 'max@gm.com', '$2a$10$XiULMB7lNqe536p7MUD7U.LOJUYpIPWKbpmuIUJBAb9uQZWYf8jRW', 1);

INSERT INTO public.user_authority
(user_id, authority_id)
VALUES(1, 4);

