<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>首页</title>
    <script type="text/javascript">
      function upes(){
       var ipname = document.getElementById("ipname").value;
       var port = document.getElementById("port").value;
       var timeout = document.getElementById("time").value;
       if(ipname == "" ){
           alert("ES服务器不能为空");
           return ;
        }
         if(port == "" ){
          alert("ES服务端口为空");
           return ;
         }
         //document.getElementById('form').submit();
         window.location.href="/update?ipname="+ipname+"&time="+timeout+"&port="+port;
      }
    
    </script>

</head>

<body>

   

        <form id="form" action="/update" method="GET">
        ES服务器(ip): <input type="text" id="ipname" name="ipname" value="" /></br>
        ES服务端口port:<input type="text" id="port" name="port" value="" /></br>
        超时时间设置(s):<input type="text" id="time" name="time" value="" /></br>
         <input type="button" id="up" name="up" value="确定"  onclick="upes();"/>
        </form>

  

</body>

</html>
