@(municipio: Municipio)(enderecos: List[models.util.EnderecoPagamento])
<!DOCTYPE html>
<html>
  <head>
    <title>Locais que receberam verbas em @municipio.getNomeMunicipio - @municipio.getSiglaEstado()</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body {
        height: 100%; margin: 0; padding: 0;
      }
      #map {
        height: 100%;
      }
    </style>
  </head>
  <body>
    <div id="map"></div>
    
<script>
function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
	  zoom: 13,
	  center: {lat: @municipio.getLatitude(), lng: @municipio.getLongitude()}
	});
	var geocoder = new google.maps.Geocoder();
	@for(end <- enderecos){
	geocodeAddress(geocoder, map, "@end.getEnderecoAjustado()");
	}
}

@*
Aqui eu vou passar também como parâmetro o cep, e o resultado eu vou mandar para o servidor
salvar, no cep, o endereço certinho e a geolocalização. por aqui são 25 mil por dia, muito melhor
do que os 2500 oficiais ;)
*@
function geocodeAddress(geocoder, resultsMap, address) {
  @*var address = document.getElementById('address').value;*@
  geocoder.geocode({'address': address}, function(results, status) {
    if (status === google.maps.GeocoderStatus.OK) {
    	console.log(results[0]);
      resultsMap.setCenter(results[0].geometry.location);
      var marker = new google.maps.Marker({
        map: resultsMap,
        position: results[0].geometry.location
      });
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}

</script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCTL1ND473lcA85N1LXOkGErd6HQquQzVw&signed_in=true&callback=initMap"
        async defer></script>
  </body>
</html>