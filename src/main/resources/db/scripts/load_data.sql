INSERT INTO users (email, password, role) VALUES
('EmailWithStrongPassword@mail.ru', '$2a$10$X/7Olfqi2AFqeYnPvnmO9uUXPyI0bMPkvIKoGBbRcX5pF4RJxCrau', 'USER'), -- PashkaM8
('DimaBilan@yandex.ru', '$2a$10$mec7gSVnJrVaSR6BpE9k1.MjdgG3ZJIw0yIqLxdh09PfVJ65cTA7a', 'USER'), -- Bilan1
('Bruno@Mars.com', '$2a$10$W1CEPiWhbq..WzomGOba.OuQ.AA7BoT0m3HVg7MQ0W1XneUVP/IDe', 'ADMIN'); -- BruBest1

INSERT INTO directories (name, user_id, parent_id) VALUES
('dir1', 1, null),
('dir2', 1, null),
('dir3', 1, 1),
('dir4', 1, 3),
('dir5', 1, 4),
('dir6', 1, 4);