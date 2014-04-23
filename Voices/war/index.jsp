<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <title>gwt-voices</title>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-587086-25']);
  _gaq.push(['_setDomainName', 'gwt-voices.appspot.com']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>

<%
String qs = request.getQueryString() != null ? "?" + request.getQueryString() : "";
%>
<body>
  <a href="/demo/VoicesDemo.html<%=qs %>">VoicesDemo.html<%=qs %></a><br/>
  <a href="/test/VoicesTest.html<%=qs %>">VoicesTest.html<%=qs %></a><br/>
</body>
</html>
