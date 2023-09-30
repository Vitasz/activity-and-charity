from flask import Flask, make_response, request
import sys

from database import SQLiter

app = Flask(__name__)
app.config["SECRET_KEY"] = "mysecretkey"
db = SQLiter()

@app.route("/")
def hello():
    return "Hello, World!"


@app.errorhandler(404)
def not_found(error):
    resp = make_response("Not found", 404)
    # resp.headers["X-Something"] = "A value"
    return resp


# работает
@app.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    
    username = data["username"]
    password = data["password"]
    
    if db.user_exists(username):
        if db.login_user(username, password):
            res = db.get_user_info(username)
            res = [res[1], res[3], res[4], res[5] if res[5] is not None else -1, res[6] if res[6] is not None else -1]
            resp = make_response(res)
            resp.set_cookie("user_id", str(db.get_user_id(username)), secure=True, httponly=True)
            resp.set_cookie("username", username, secure=True, httponly=True)
            return resp
        else:
            return "Invalid login or password", 403
    else:
        return "User does not exist", 403


# работает
@app.route("/login_supervisor", methods=["POST"])
def login_supervisor():
    data = request.get_json()
    username = data["username"]
    password = data["password"]
    if db.supervisor_exists(username):
        if db.login_supervisor(username, password):
            resp = make_response("Logged in successfully")
            resp.set_cookie("supervisor_id", str(db.get_supervisor_id(username)), secure=True, httponly=True)
            resp.set_cookie("supervisor_username", username, secure=True, httponly=True)
            return resp
        else:
            return "Invalid login or password", 403
    else:
        return "User does not exist", 403


# работает
@app.route("/get_activities", methods=["GET"])
def get_activities():
    user_id = request.cookies.get("user_id")
    return db.get_user_activities(user_id)


# работает
@app.route("/get_top", methods=["GET"])
def get_top():
    return db.get_top_users()


# работает
@app.route("/get_activities_supervisor", methods=["GET"])
def get_activities_supervisor():
    id_subdivision = request.cookies.get("supervisor_id")
    return db.get_subdivision_activities(id_subdivision)


# работает
@app.route("/get_funds", methods=["GET"])
def get_funds():
    return db.get_funds()


# работает
@app.route("/register_user", methods=["POST"])
def register_user():
    data = request.get_json()
    username = data["username"]
    password = data["password"]
    email = data["email"]
    name = data["name"]
    if db.user_exists(username):
        return "User already exists", 403
    db.add_user(username, password, email, name)
    return "User registered", 200


# работает
@app.route("/register_supervisor", methods=["POST"])
def register_supervisor():
    data = request.get_json()
    username = data["username"]
    password = data["password"]
    email = data["email"]
    name = data["name"]
    if db.supervisor_exists(username):
        return "Supervisor already exists", 403
    db.add_supervisor(username, password, email, name)
    return "Supervisor registered"


# работает
@app.route("/register_fund", methods=["POST"])
def register_fund():
    data = request.get_json()
    username = data["username"]
    password = data["password"]
    email = data["email"]
    name = data["name"]
    if db.fund_exists(username):
        return "Fund already exists", 403
    db.add_fund(username, password, email, name)
    return "Fund registered"


# работает
@app.route("/add_activity", methods=["POST"])
def add_activity():
    data = request.get_json()
    user_id = request.cookies.get("user_id")
    type_id = data["typeId"]
    value = data["value"]
    date = data["date"]
    db.add_activity(user_id, type_id, value, date)
    return "Activity added"


# работает
@app.route("/select_fund", methods=["POST"])
def select_fund():
    data = request.get_json()
    user_id = request.cookies.get("user_id")
    fund_id = data["fundId"]
    db.select_fund(user_id, fund_id)
    return "Fund selected", 200


# работает
@app.route("/select_subdivision", methods=["POST"])
def select_subdivision():
    data = request.get_json()
    user_id = request.cookies.get("user_id")
    subdivision_id = data["subdivisionId"]
    db.select_subdivision(user_id, subdivision_id)
    return "Subdivision selected", 200


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8000)

