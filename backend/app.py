from flask import Flask, make_response, request
import re

from database import SQLiter


def check_email(email):
    pattern = r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
    if re.match(pattern, email) and email != "":
        return True
    else:
        return False


def check_password(password):
    # regexp check for SHA256 hash
    pattern = r"^[a-fA-F0-9]{64}$"
    if re.match(pattern, password) and password != "":
        return True
    else:
        return False


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
    if "username" not in data or "password" not in data:
        return "Invalid request", 400

    username = data["username"]
    password = data["password"]

    if db.user_exists(username):
        if db.login_user(username, password):
            res = db.get_user_info(username)
            res = {
                "username": res[1],
                "email": res[3],
                "name": res[4],
                "selectedFund": res[5] if res[5] is not None else -1,
                "idSubdivision": res[6] if res[6] is not None else -1,
            }
            resp = make_response(res)
            resp.set_cookie(
                "user_id", str(db.get_user_id(username)), secure=True, httponly=True
            )
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
    if "username" not in data or "password" not in data:
        return "Invalid request", 400

    username = data["username"]
    password = data["password"]
    if db.supervisor_exists(username):
        if db.login_supervisor(username, password):
            res = db.get_supervisor_info(username)
            res = {
                "username": res[1],
                "email": res[3],
                "name": res[4],
                "selectedFund": res[5] if res[5] is not None else -1,
            }
            resp = make_response(res)
            resp.headers.add("Access-Control-Allow-Origin", "*")
            resp.headers.add("Access-Control-Allow-Headers", "*")
            resp.headers.add("Access-Control-Allow-Methods", "*")
            resp.set_cookie(
                "supervisor_id",
                str(db.get_supervisor_id(username)),
                secure=True,
                httponly=True,
            )
            resp.set_cookie("supervisor_username", username, secure=True, httponly=True)
            return resp
        else:
            return "Invalid login or password", 403
    else:
        return "User does not exist", 403


# работает
@app.route("/get_activities", methods=["GET"])
def get_activities():
    if "user_id" not in request.cookies:
        return "Not authorized", 403
    user_id = request.cookies.get("user_id")
    res = db.get_user_activities(user_id)
    res = [{"idType": i[1], "value": i[2], "date": i[3]} for i in res]
    return res, 200


# работает
@app.route("/get_top", methods=["GET"])
def get_top():
    res = db.get_top_users()
    res = [{"name": i[0], "value": i[1]} for i in res]
    return res, 200


# работает
@app.route("/get_activities_supervisor", methods=["GET"])
def get_activities_supervisor():
    if "supervisor_id" not in request.cookies:
        return "Not authorized", 403
    id_subdivision = request.cookies.get("supervisor_id")
    res = db.get_subdivision_activities(id_subdivision)
    res = [{"idType": i[1], "value": i[2], "date": i[3]} for i in res]
    return res, 200


# работает
@app.route("/get_funds", methods=["GET"])
def get_funds():
    return db.get_funds()


# работает
@app.route("/get_fund_by_id", methods=["GET"])
def get_fund_by_id():
    if 'id' not in request.args:
        return 'Invalid request', 400
    id = request.args.get('id')
    return db.get_fund_info(id)


@app.route("/get_subdivision_by_id", methods=["GET"])
def get_subdivision_by_id():
    if 'id' not in request.args:
        return 'Invalid request', 400
    id = request.args.get('id')
    return db.get_subdivision_info(id)

# работает
@app.route("/register_user", methods=["POST"])
def register_user():
    data = request.get_json()
    if (
        "username" not in data
        or "password" not in data
        or "email" not in data
        or "name" not in data
    ):
        return "Invalid request", 400
    username = data["username"]
    password = data["password"]
    email = data["email"]
    name = data["name"]
    if db.user_exists(username):
        return "User already exists", 403
    if check_email(email) and check_password(password):
        db.add_user(username, password, email, name)
        resp = make_response("User registered", 200)
        resp.set_cookie(
            "user_id", str(db.get_user_id(username)), secure=True, httponly=True
        )
        resp.set_cookie("username", username, secure=True, httponly=True)
        return resp
    return "Invalid email or password", 403


# работает
@app.route("/register_supervisor", methods=["POST"])
def register_supervisor():
    data = request.get_json()
    if (
        "username" not in data
        or "password" not in data
        or "email" not in data
        or "name" not in data
    ):
        return "Invalid request", 400
    username = data["username"]
    password = data["password"]
    email = data["email"]
    name = data["name"]
    if db.supervisor_exists(username):
        return "Supervisor already exists", 403
    if check_email(email) and check_password(password):
        resp = make_response("Supervisor registered", 200)
        resp.set_cookie(
            "user_id", str(db.get_user_id(username)), secure=True, httponly=True
        )
        resp.set_cookie("username", username, secure=True, httponly=True)
        db.add_user(username, password, email, name)
        return resp
    return "Invalid email or password", 403


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
    if (
        "typeId" not in data
        or "value" not in data
        or "date" not in data
        or "user_id" not in request.cookies
    ):
        return "Invalid request", 400
    user_id = request.cookies.get("user_id")
    type_id = data["typeId"]
    value = data["value"]
    date = data["date"]
    db.add_activity(user_id, type_id, value, date)
    return "Activity added", 200


# работает
@app.route("/select_fund", methods=["POST"])
def select_fund():
    data = request.get_json()
    if "fundId" not in data or "user_id" not in request.cookies:
        return "Invalid request", 400
    user_id = request.cookies.get("user_id")
    fund_id = data["fundId"]
    db.select_fund(user_id, fund_id)
    return "Fund selected", 200


# работает
@app.route("/select_subdivision", methods=["POST"])
def select_subdivision():
    data = request.get_json()
    if "subdivisionId" not in data or "user_id" not in request.cookies:
        return "Invalid request", 400
    user_id = request.cookies.get("user_id")
    subdivision_id = data["subdivisionId"]
    db.select_subdivision(user_id, subdivision_id)
    return "Subdivision selected", 200


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000)
