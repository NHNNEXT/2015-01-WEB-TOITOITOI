if ('geolocation' in navigator) {
	navigator.geolocation.getCurrentPosition(
    changePosition.bind(this, 'lowAcc'),
    logError.bind(this, 'lowAcc'),
    {
      timeout : 20000, // ms
      maximumAge : 10800000 // 3hr to ms
    }
  );
	navigator.geolocation.getCurrentPosition(
    changePosition.bind(this, 'highAcc'),
    logError.bind(this, 'highAcc'),
    {
      enableHighAccuracy : true,
      maximumAge : 1800000 // 30min to ms
    }
  );
  var watchID = navigator.geolocation.watchPosition(
    changePosition.bind(this, 'watch'),
    logError.bind(this, 'watch'),
    {
      maximumAge : 120000 // 2min to ms
    }
  );
} else {
  console.log('browser doesn\'t support geolocation');
}

function changePosition (calledFrom, position) {
  console.log(calledFrom, position);
  // ajax 되고 나면 해야지 \^^/
}

function logError (calledFrom, error) {
  switch (error.code) {
    case error.PERMISSION_DENIED:
    // 한 번 block하고 나면 다시 안 물어본다는 게 함정 ㅜㅜ
      break;
    case error.TIMEOUT:
      break;
    case error.POSITION_UNAVAILABLE:
     case error.UNKNOWN_ERROR:
      debugger;
      break;
    default:
      console.error(calledFrom, error.message);
  }
}
