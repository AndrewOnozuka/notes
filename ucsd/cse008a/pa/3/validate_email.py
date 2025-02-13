def validate_email(email_address,email_suffix):
    email_address = email_address.strip()
    email_suffix = email_suffix.strip()
    if email_address.count("@") + email_suffix.count("@") == 1:
        if " " in email_address or " " in email_suffix:
            return False
        if "ucsd.edu" in email_suffix:
            return False
        if email_suffix in email_address:
            return True
        else:
            return True
    else:
        return False