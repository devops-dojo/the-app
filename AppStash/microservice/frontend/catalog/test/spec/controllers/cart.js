'use strict';

describe('Controller: CartController', function () {

  // load the controller's module
  beforeEach(module('eshop'));

  var CartCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CartCtrl = $controller('CartController', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
