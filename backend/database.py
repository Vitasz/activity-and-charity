import sqlite3


class SQLiter:
    def __init__(self):
        # Connect to database
        self.connection = sqlite3.connect("database.db", check_same_thread=False)
        self.cursor = self.connection.cursor()

    def user_exists(self, username):
        self.cursor.execute("SELECT * FROM users WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return len(res) > 0
    
    def supervisor_exists(self, username):
        self.cursor.execute("SELECT * FROM supervisors WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return len(res) > 0
    
    def fund_exists(self, username):
        self.cursor.execute("SELECT * FROM funds WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return len(res) > 0
    
    def get_user_info(self, username):
        self.cursor.execute("SELECT * FROM users WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return res[0]
    
    def get_supervisor_info(self, username):
        self.cursor.execute("SELECT * FROM supervisors WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return res[0]
    
    def get_fund_info(self, username):
        self.cursor.execute("SELECT * FROM funds WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return res[0]
    
    def login_user(self, username, password):
        self.cursor.execute("SELECT * FROM users WHERE username = ? AND password = ?", (username, password))
        res = self.cursor.fetchall()
        return len(res) > 0
    
    def login_supervisor(self, username, password):
        self.cursor.execute("SELECT * FROM supervisors WHERE username = ? AND password = ?", (username, password))
        res = self.cursor.fetchall()
        return len(res) > 0
    
    def get_user_id(self, username):
        self.cursor.execute("SELECT id FROM users WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return res[0][0]
    
    def get_supervisor_id(self, username):
        self.cursor.execute("SELECT id FROM supervisors WHERE username = ?", (username,))
        res = self.cursor.fetchall()
        return res[0][0]

    def add_user(self, username, password, email, name):
        self.cursor.execute(
            "INSERT INTO users(username, password, email, name) VALUES (?, ?, ?, ?)",
            (username, password, email, name),
        )
        self.connection.commit()

    def add_supervisor(self, username, password, email, name):
        self.cursor.execute(
            "INSERT INTO supervisors(username, password, email, name) VALUES (?, ?, ?, ?)",
            (username, password, email, name),
        )
        self.connection.commit()

    def add_fund(self, username, password, email, name):
        self.cursor.execute(
            "INSERT INTO funds(username, password, email, name) VALUES (?, ?, ?, ?)",
            (username, password, email, name),
        )
        self.connection.commit()

    def add_subdivision(self, name):
        self.cursor.execute("INSERT INTO subdivisions(name) VALUES (?)", (name,))
        self.connection.commit()

    def select_fund(self, user_id, fund_id):
        self.cursor.execute(
            "UPDATE users SET selected_fund = ? WHERE id = ?", (fund_id, user_id)
        )
        self.connection.commit()

    def select_subdivision(self, user_id, subdivision_id):
        self.cursor.execute(
            "UPDATE users SET id_subdivision = ? WHERE id = ?",
            (subdivision_id, user_id),
        )
        self.connection.commit()

    def select_supervisor(self, supervisor_id, subdivision_id):
        self.cursor.execute(
            "UPDATE supervisors SET id_subdivision = ? WHERE id = ?",
            (subdivision_id, supervisor_id),
        )
        self.connection.commit()

    def add_activity_type(self, name, coefficient):
        self.cursor.execute(
            "INSERT INTO types_activities(name, coefficient) VALUES (?, ?)",
            (name, coefficient),
        )
        self.connection.commit()

    def add_activity(self, user_id, type_id, value, date):
        self.cursor.execute(
            "INSERT INTO activities(id_user, id_type, value, date) VALUES (?, ?, ?, ?)",
            (user_id, type_id, value, date),
        )
        self.connection.commit()

    def get_user_activities(self, user_id):
        self.cursor.execute("SELECT * FROM activities WHERE id_user = ?", (user_id,))
        res = self.cursor.fetchall()
        return [i[1:] for i in res]

    def get_top_users(self):
        self.cursor.execute(
            "SELECT id_user, SUM(value * (SELECT coefficient FROM types_activities WHERE id == id_type)) AS total_sum FROM activities GROUP BY id_user ORDER BY total_sum DESC LIMIT 10"
        )
        res = self.cursor.fetchall()
        for i in range(len(res)):
            self.cursor.execute(
                "SELECT name FROM users WHERE id = ?", (res[i][0],)
            )
            res[i] = self.cursor.fetchall()[0] + res[i][1:]
        return res

    def get_subdivision_activities(self, subdivision_id):
        self.cursor.execute(
            "SELECT * FROM activities WHERE id_user = (SELECT id FROM users WHERE id_subdivision = ?)",
            (subdivision_id,),
        )
        res = self.cursor.fetchall()
        return [i[1:] for i in res]

    def get_funds(self):
        self.cursor.execute("SELECT name FROM funds")
        res = self.cursor.fetchall()
        return [i[0] for i in res]
