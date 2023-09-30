gunicorn wsgi:app \
    --workers 4 \
    --bind 0.0.0.0:8000 \
    --log-file logs/gunicorn.log \
    --log-level DEBUG \
    --reload
