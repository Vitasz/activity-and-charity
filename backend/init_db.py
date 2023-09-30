import sqlite3

connection = sqlite3.connect('database.db')

with open('schema.sql') as f:
    connection.executescript(f.read())

cur = connection.cursor()

cur.execute("INSERT INTO users(username, password, email, name) VALUES (?, ?, ?, ?)",
            ('johnsmith', 'password123', 'johnsmith@example.com', 'John')
)

cur.execute("INSERT INTO supervisors(username, password, email, name) VALUES (?, ?, ?, ?)",
            ('smith', 'password123', 'smith@example.com', 'Smith')
)

cur.execute("INSERT INTO types_activities(name, coefficient) VALUES (?, ?)",
            ('Jogging', 1.0)
)

cur.execute("INSERT INTO activities(id_user, id_type, value, date) VALUES (?, ?, ?, ?)",
            (1, 1, 5.0, '2020-05-01')
)

cur.execute("INSERT INTO funds(username, password, email, name) VALUES (?, ?, ?, ?)",
            ('kittens', 'password123', 'kittens@example.com', 'Kittens')
)

cur.execute("INSERT INTO subdivisions (name) VALUES (?)",
    ('Programmers',)
)

connection.commit()
connection.close()
