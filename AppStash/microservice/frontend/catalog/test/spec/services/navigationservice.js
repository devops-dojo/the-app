'use strict';

describe('Service: navigationService', function () {

  // load the service's module
  beforeEach(module('eshop'));

  // instantiate service
  var navigationService;
  beforeEach(inject(function (_navigationService_) {
    navigationService = _navigationService_;
  }));

  it('should do something', function () {
    expect(!!navigationService).toBe(true);
  });

});
