struct server_message {
    message_id: String,
    sync: u8,
    message_type: String,
    data: String,
}


fn is_sync() -> bool{
    sync == 1;
}
