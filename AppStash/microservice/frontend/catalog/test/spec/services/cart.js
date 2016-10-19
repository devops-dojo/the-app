'use strict';

describe('Service: Cart', function () {

  // load the service's module
  beforeEach(module('eshop'));

  // instantiate service
  var cart;
  beforeEach(inject(function (_cart_) {
    cart = _cart_;
  }));

  it('should do something', function () {
    expect(!!cart).toBe(true);
  });

});
