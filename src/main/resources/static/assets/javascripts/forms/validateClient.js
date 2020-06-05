function validationClient(){

    var clientName = document.forms["clientAdd"]["recipient-name"];
    var clientMail = document.forms["clientAdd"]["recipient-mail"];
    var clientCif = document.forms["clientAdd"]["recipient-cif"];
    var clientDate = document.forms["clientAdd"]["mydate"];
    var clientAdress = document.forms["clientAdd"]["recipient-adress"];
    var clientContact = document.forms["clientAdd"]["recipient-contact"];

    if(clientName.value == '')
    {
     document.getElementById("validation-name").innerHTML = "Please insert client name";
     return false;
    } else if(clientMail.value =='') {
    document.getElementById("validation-name").innerHTML = "";
    document.getElementById("validation-mail").innerHTML = "Please insert client mail";
    return false;
     } else if(clientCif.value =='') {
     document.getElementById("validation-mail").innerHTML = "";
     document.getElementById("validation-cif").innerHTML = "Please insert client cif";
     return false;
     } else if(clientDate.value =='') {
     document.getElementById("validation-cif").innerHTML = "";
     document.getElementById("validation-date").innerHTML = "Please insert client date";
      return false;
     } else if(clientAdress.value == '') {
      document.getElementById("validation-date").innerHTML = "";
      document.getElementById("validation-adress").innerHTML = "Please insert client adress";
      return false;
     } else if(clientContact.value == '') {
      document.getElementById("validation-adress").innerHTML = "";
      document.getElementById("validation-contact").innerHTML = "Please insert client contact";
      return false;
     } else {
    return true;
    }
}
 $('#mydate').datepicker({
     dateFormat: 'dd-mm-yyyy',
 });