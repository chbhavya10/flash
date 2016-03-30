var mainApp1 = angular.module("mainApp1", []);
mainApp1.controller("contactInfoController", function($scope,$http) {
	 $http.get('http://localhost:8080/SermonNote/userlist/rest/users').
     success(function(data) {
         $scope.posts = data;
     });
});