if [ ! -d "venv" ]
then
    python -m venv venv
    pip install -r requirements.txt
fi

flask run
