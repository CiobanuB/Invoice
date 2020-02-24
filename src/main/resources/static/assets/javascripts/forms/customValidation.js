// Save / Update form
function validation(){

    var password = document.getElementById('password').value;
    var confirmation_password = document.getElementById('confirmation_password').value;
    var firstname = document.getElementById('firstname').value;
    var lastname = document.getElementById('lastname').value;
    var age = document.getElementById('age').value;

    if(firstname == '')
    {
        document.getElementById("validation-firstname").innerHTML = "Please insert your firstname";
        return false;
    } else if(lastname=='') {
    document.getElementById("validation-lastname").innerHTML = "Please insert your lastname";
    return false;
     } else if(age=='') {
     document.getElementById("validation-age").innerHTML = "Please insert your age";
     return false;
     } else if(password=='') {
      document.getElementById("validation-password").innerHTML = "Please insert your password";
      return false;
     } else if(confirmation_password =='' || confirmation_password !== password) {
        document.getElementById("validation-confirmation-pwd").innerHTML = "Confirmation is not the same as the password";
        return false;
        } else {
    return true;
    }
}