/// <reference path="types.ts"/>

var _:UnderscoreStatic;
var $:JQueryStatic;

var eshop = angular.module('eshop', [
    'ui.bootstrap',
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'eshop.config',
    'LocalStorageModule'
]).config(($routeProvider:ng.route.IRouteProvider) => {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html'
        })
        .when('/productcatalog/:productType', {
            templateUrl: 'views/catalog.html'
        }).when('/productdetail/:urlname', {
            templateUrl: 'views/detail.html'
        }).otherwise({
            templateUrl: 'views/404.html'
        });
}).config(['localStorageServiceProvider', (localStorageServiceProvider) => {
    localStorageServiceProvider.setPrefix('eshop');
}]);



