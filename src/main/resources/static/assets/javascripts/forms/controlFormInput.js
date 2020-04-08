// Save / Update form
function validationForm(clickID){
            var table = document.getElementById("datatable-editable");
            var main = document.getElementById("main-panel");
     		var inputs = main.getElementsByTagName("input");

        for (var i = 1 ; i < table.rows.length; i++) {
             if(i == clickID){
            for (var j = 1; j < table.rows[i].cells.length-1; j++) {
                        inputs[j-1].value = table.rows[i].cells[j].innerHTML;
                 }
            }
        }


}