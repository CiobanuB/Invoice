function validate_signUp(){

    var confirmation_password = document.getElementById('pwd_confirm').value;
    var password = document.getElementById('pwd').value;
    var firstName = document.getElementById('firstName').value;
    var password = document.getElementById('pwd').value;

     if( firstName =='') {
        document.getElementById("errorFirstname").innerHTML = "Please fill the First Name";
        return false;
     } else if( password =='') {
        document.getElementById("errorPassword").innerHTML = "Please fill the password";
        return false;
    } else if( confirmation_password =='') {
        document.getElementById("errorConfirm").innerHTML = "Please fill the confirmation ";
        return false;
    } else if(confirmation_password =='' || confirmation_password !== password) {
        document.getElementById("errorConfirm").innerHTML = "Confirmation password is not the same as the password";
        return false;
    } else {
        return true;
    }


}