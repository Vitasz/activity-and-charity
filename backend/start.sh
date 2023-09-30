if [ ! -d "venv" ]
then
    python3 -m venv venv
    source venv/bin/activate
    pip install -r requirements.txt
fi

source venv/bin/activate
python3 init_db.py
flask run --host=0.0.0.0
