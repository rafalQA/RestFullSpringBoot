var app = angular.module('app', ['ngRoute'])

  app.config(function($routeProvider){
    $routeProvider
    .when('/', {
      templateUrl: 'login.html',
    })
    .when('/userDashboard', {
     templateUrl: 'userDashboard.html',
    })
  });

app.controller('log',
function($rootScope, $http, $location, $scope) {

  this.credentials = {};

  $scope.authenticate = function() {

    var headers = this.credentials ? {authorization : "Basic "
        + btoa(this.credentials.username + ":" + this.credentials.password)
    } : {};

    $rootScope.authHeader = headers;

    $http.get('user', {headers : headers}).then(function(response) {
       if (response.data.name) {
          $rootScope.user = response.data;
        if(response.data.authorities[0] === "USER"){
          $location.path("/userDashboard");
        }else{
          $location.path("/");
        }
      } else {
      }
    });
  }
})
.controller('userProp', function($rootScope,$http, $scope){

  var user = $rootScope.user;

  $scope.retrieveProps = function(){
     $http.get('/users/' +  user.principal.accountId + '/properties', {headers : $rootScope.authHeader})
      .then(function(response){
          $scope.properties = response.data;
      })
   }
  $scope.getCurrency = function(){
     $http.get('/currency').then(function(response){
        $scope.currency = response.data;
     })
   }

  $scope.addProperty =  function(){

   var propertyWithoutId = {name: $scope.name, value: $scope.value};

   $http.post('/users/' + user.principal.accountId  + '/properties', propertyWithoutId);

   var propertyId = 1;

   if($scope.properties.length > 0){
     propertyId = Math.max.apply(Math, $scope.properties.map(function(item){return item.propertyId})) + 1
      }

   var propertyWithId = {name: $scope.name, value: $scope.value, propertyId: propertyId};

   $scope.name = "";
   $scope.value = "";

   $scope.properties.push(propertyWithId);
  }

  $scope.removeProperty = function(idx){
     var propToDelete = $scope.properties[idx]

     $http.delete('/properties/' + propToDelete.propertyId).then(function(response){
        $scope.properties.splice(idx, 1);
     })
  }

  $scope.convertPropertyValue = function(){
    var propertyId = $scope.propId;
    var foreignCurrency = $scope.selectedCurrency;

    $http.get('/properties/' + propertyId + '/?currency=' + foreignCurrency).then(function(response){
       $scope.valueForeignCurrency = response.data;
    })

  }
})
