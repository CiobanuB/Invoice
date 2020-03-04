function validation(){

    var getPath = document.getElementById('getPath').value;

     if(getPath == '')
        {
            document.getElementById("error-path").innerHTML = "Please insert your default folder";
            return false;
        }
        return true;
}