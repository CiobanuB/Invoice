// Save / Update form
function validation(){

    var companyName = document.getElementById('companyName').value;
    var companyCif = document.getElementById('companyCif').value;
    var regDate = document.getElementById('regDate').value;
    var adress = document.getElementById('adress').value;
    var mail = document.getElementById('mail').value;
    var website = document.getElementById('website').value;
    var bankAccount = document.getElementById('bankAccount').value;

    if(companyName == '')
    {
        document.getElementById("validation-name").innerHTML = "Please insert your companyName";
        return false;
    } else if(companyCif =='') {
    document.getElementById("validation-cif").innerHTML = "Please insert your companyCif";
    return false;
     } else if(regDate =='') {
     document.getElementById("validation-date").innerHTML = "Please insert your regDate";
     return false;
     } else if(mail =='') {
          document.getElementById("validation-mail").innerHTML = "Please insert your mail";
          return false;
     } else if(adress =='') {
      document.getElementById("validation-adress").innerHTML = "Please insert your adress";
      return false;
     } else if(website =='') {
      document.getElementById("validation-website").innerHTML = "Please insert your website";
      return false;
     } else if(bankAccount =='') {
      document.getElementById("validation-bank").innerHTML = "Please insert your bankAccount";
      return false;
      } else {
    return true;
    }
}