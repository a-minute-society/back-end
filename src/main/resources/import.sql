INSERT INTO categories (category_name) VALUES ('IT');
INSERT INTO categories (category_name) VALUES ('Economy/Finance');
INSERT INTO categories (category_name) VALUES ('lifestylcategoriese');
INSERT INTO categories (category_name) VALUES ('Cooking/Baking');
INSERT INTO categories (category_name) VALUES ('Exercise/Fitness');
INSERT INTO categories (category_name) VALUES ('Photo/Video');
INSERT INTO categories (category_name) VALUES ('programming');
INSERT INTO categories (category_name) VALUES ('marketing');
INSERT INTO categories (category_name) VALUES ('design');
INSERT INTO categories (category_name) VALUES ('Video/Mindset');

INSERT INTO videos (category_id, run_time, video_title, url) VALUES (1, 567, 'IT 제목1', 'https://example.com/1/video1');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (1, 600, 'IT 제목2', 'https://example.com/1/video2');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (1, 720, 'IT 제목3', 'https://example.com/1/video3');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (1, 480, 'IT 제목4', 'https://example.com/1/video4');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (2, 1020, '경제 제목1', 'https://example.com/2/video1');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (2, 480, '경제 제목2', 'https://example.com/2/video2');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (2, 1260, '경제 제목3', 'https://example.com/2/video3');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (2, 300, '경제 제목4', 'https://example.com/2/video4');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (3, 420, '일상 제목1', 'https://example.com/3/video1');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (3, 600, '일상 제목2', 'https://example.com/3/video2');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (3, 480, '일상 제목3', 'https://example.com/3/video3');
INSERT INTO videos (category_id, run_time, video_title, url) VALUES (3, 840, '일상 제목4', 'https://example.com/3/video4');

INSERT INTO users (user_id, user_name, user_pw) VALUES ('hello', 'a', '1234');