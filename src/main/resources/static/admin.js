angular.module('admin', [])
    .controller('Admin', function($scope, $http, $timeout) {
        $scope.users = [];
        $scope.visibleUsers = false;
        $scope.resultDelete = "User still exist";
        $scope.statusVisible = false;

        $scope.get = function() {
            $http.get('http://localhost:8080/users').
            then(function(response) {
                $scope.users = response.data;
            });
            $scope.visibleUsers = !$scope.visibleUsers;
        }

        $scope.delete = function(value) {

            $http.delete('http://localhost:8080/users/' + value).
            then(function(response) {
                if (response.status == 204) {
                    $scope.resultDelete = response;
                    $scope.resultDelete = "User has been deleted";
                    $scope.statusVisible = true
                    $timeout(function() {
                        $scope.statusVisible = false;
                    },3000);
                }
            })
        }
    });