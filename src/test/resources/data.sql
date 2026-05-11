INSERT INTO students (id, name, kana, age, nickname, email, region, gender, remark, is_deleted)
VALUES
    ('stu1', '山田太郎', 'やまだたろう', 25, 'たろう', 'taro@example.com', '東京', 'male', '特になし', 0),
    ('stu2', '佐藤花子', 'さとうはなこ', 22, 'はな', 'hana@example.com', '大阪', 'female', NULL, 0),
    ('stu3', '中村優', 'なかむらゆう', 30, NULL, 'yu@example.com', '福岡', 'nonbinary', 'メモあり', 0),
    ('stu4', '鈴木健', 'すずきけん', 28, 'けん', 'ken@example.com', '北海道', 'male', NULL, 0),
    ('stu5', '田中美咲', 'たなかみさき', 27, 'みさき', 'misaki@example.com', '愛知', 'female', '注意事項あり', 0);

INSERT INTO students_courses (id, students_id, course_name, start_date, expected_end_date)
VALUES
    ('crs1', 'stu1', 'Java基礎', '2024-04-01', '2024-06-30'),
    ('crs2', 'stu1', 'Spring入門', '2024-07-01', '2024-09-30'),
    ('crs3', 'stu2', 'データベース基礎', '2024-05-01', '2024-07-31'),
    ('crs4', 'stu4', 'Linux基礎', '2024-03-15', '2024-05-31'),
    ('crs5', 'stu5', 'ネットワーク入門', '2024-06-01', '2024-08-31');
