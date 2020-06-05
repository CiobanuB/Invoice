function resetValidation(){

    //var mail = document.forms["clientAdd"]["recipient-name"];
    var pwd = document.getElementById('change-mail').value;
    var pwdConfirm = document.getElementById('confirmPwd').value;
    /*if(mail == '')
    {
       document.getElementById("validation-pwd-recover").innerHTML = "Please insert client name";
       return false;
    } else*/
    if(pwd !== pwdConfirm)
    {
        document.getElementById("changePwdValidation").innerHTML = "Confirmation is not the same as the password";
        return false;
    }
    confirmation_password !== password
    else  {
        return true;
    }

}