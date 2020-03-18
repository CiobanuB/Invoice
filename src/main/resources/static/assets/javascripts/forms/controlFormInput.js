// Save / Update form
function validationForm(){

     var table = document.getElementById("datatable-editable");
     var tableLenght = document.getElementById("datatable-editable").rows.length;
     var x = document.getElementById("main-panel");
     var y = x.getElementsByTagName("input");

     for (var i = 0 ; i < table.rows.length-1; i++) {
       var date = document.getElementById("date"+i).value;
       var mail = document.getElementById("mail"+i).value;
       var cif = document.getElementById("cif"+i).value;
       var adress = document.getElementById("adress"+i).value;
       var contact = document.getElementById("contact"+i).value;

       document.getElementById("inputDate" + i ).innerHTML = date;
       document.getElementById("inputMail"+ i).innerHTML = mail;
       document.getElementById("inputCif" + i).innerHTML = cif;
       document.getElementById("inputAdress" + i).innerHTML = adress;
       document.getElementById("inputContact" + i ).innerHTML = contact;

     }
}