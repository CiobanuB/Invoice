function resetValidation(){

    //var mail = document.forms["clientAdd"]["recipient-name"];
    var mail = document.getElementById('recover-mail').value;
    if(mail == '')
    {
       document.getElementById("validation-pwd-recover").innerHTML = "Please insert client name";
       return false;
    } else  {
        return true;
    }

}