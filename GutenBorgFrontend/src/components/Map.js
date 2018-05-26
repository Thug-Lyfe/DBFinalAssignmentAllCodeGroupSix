import React from 'react';
import { GoogleApiWrapper, InfoWindow, Map, Marker } from 'google-maps-react';
import Paper from '@material-ui/core/Paper';


class GoogleMapsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showingInfoWindow: false,
      activeMarker: {},
      selectedPlace: {}
    }
    // binding this to event-handler functions
    this.onMarkerClick = this.onMarkerClick.bind(this);
    this.onMapClick = this.onMapClick.bind(this);
  }
  onMarkerClick = (props, marker, e) => {
      console.log(marker)
    this.setState({
      selectedPlace: props,
      activeMarker: marker,
      showingInfoWindow: true
    });
  }
  onMapClick = (props) => {
    if (this.state.showingInfoWindow) {
      this.setState({
        showingInfoWindow: false,
        activeMarker: null
      });
    }
  }
  render() {
    const style = {
        width: '100%',
        height: '90%',
        paddingBottom: '10px',
        marginTop: '20px'
    }
    return (
      <Map
        item
        xs = { 12 }
        style = { style }
        containerStyle={{position: 'relative', width: '100%', height: '900px' }}
        google = { this.props.google }
        onClick = { this.onMapClick }
        zoom = { 4 }
        initialCenter = {{ lat: 53.648209, lng: 20.711185 }}
      >
        {this.props.data.map((city, i) => {
            return <Marker
                    key = {i}
                    onClick = { this.onMarkerClick }
                    title = { city.cityName}
                    position = {{ lat: city.geoLocation.lat, lng: city.geoLocation.lang }}
                    name = { city.cityName }/>
        })}
        
      </Map>
    );
  }
}
export default GoogleApiWrapper({
    api: "AIzaSyCu7L1Cc212OZKjqBpEStL6z97IGjLeWt8"
})(GoogleMapsContainer)


