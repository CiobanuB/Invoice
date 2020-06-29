function validateFolder(){

    //var mail = document.forms["clientAdd"]["recipient-name"];
    var pathFolder = document.getElementById('getPath').value;
    if(pathFolder == '')
    {
        document.getElementById("path-verification").innerHTML = "Please insert your path folder";
        return false;
    }
        return true;

}
function validateInvoice(){

     var clientNameInvoice = document.getElementById('clientNameInvoice').value;
     var dateInvoice = document.getElementById('dateInvoice').value;
     var invoiceSeriesInvoice = document.getElementById('invoiceSeriesInvoice').value;
     var serviceNameInvoice = document.getElementById('serviceNameInvoice').value;
     var piecesInvoice = document.getElementById('piecesInvoice').value;
     var unitMeasureInvoice = document.getElementById('unitMeasureInvoice').value;
     var unitPriceInvoice = document.getElementById('unitPriceInvoice').value;
     var sumInvoice = document.getElementById('sumInvoice').value;


     if(clientNameInvoice == '')
         {
          document.getElementById("validation-nameInvoice").innerHTML = "Please insert client name";
          return false;
         } else if(dateInvoice =='') {
         document.getElementById("validation-nameInvoice").innerHTML = "";
         document.getElementById("validation-dateInvoice").innerHTML = "Please insert client mail";
         return false;
          } else if(invoiceSeriesInvoice =='') {
          document.getElementById("validation-dateInvoice").innerHTML = "";
          document.getElementById("validation-invoiceSeries").innerHTML = "Please insert client cif";
          return false;
          } else if(serviceNameInvoice =='') {
          document.getElementById("validation-invoiceSeries").innerHTML = "";
          document.getElementById("validation-serviceName").innerHTML = "Please insert client date";
           return false;
          } else if(piecesInvoice == '') {
           document.getElementById("validation-serviceName").innerHTML = "";
           document.getElementById("validation-pieces").innerHTML = "Please insert client adress";
           return false;
          } else if(unitMeasureInvoice == '') {
           document.getElementById("validation-pieces").innerHTML = "";
           document.getElementById("validation-unitMeasure").innerHTML = "Please insert client contact";
           return false;
          } else if(unitPriceInvoice == '') {
           document.getElementById("validation-unitMeasure").innerHTML = "";
           document.getElementById("validation-unitPrice").innerHTML = "Please insert client contact";
           return false;
          }
          else if(sumInvoice == '') {
           document.getElementById("validation-unitPrice").innerHTML = "";
           document.getElementById("validation-sumInvoice").innerHTML = "Please insert client contact";
           return false;
           }
        return true;

}
 $('#dateInvoice').datepicker({
     format: 'dd-MM-yyyy'
 });