describe('PlaceListCtrl', function() {
  var scope, $httpBackend, ctrl;

  beforeEach(function() {
    this.addMatchers({
      toEqualData: function(expected) {
        return angular.equals(this.actual, expected);
      }
    });
  });

  beforeEach(module('vita'));

  beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $routeParams, TestData) {
    $httpBackend = _$httpBackend_;
    $httpBackend.expectGET('webapi/documents/123/places').respond(TestData.places);
    $httpBackend.expectGET('webapi/documents/123').respond(TestData.singleDocument);
    $httpBackend.expectGET('webapi/documents/123/parts').respond(TestData.parts);
    $routeParams.documentId = '123';
    scope = $rootScope.$new();
    ctrl = $controller('PlaceListCtrl', {
      $scope: scope
    });
  }));

  it('should create "place" model with 2 places', inject(function($controller, TestData) {

    expect(scope.places).toEqualData([]);
    $httpBackend.flush();
    expect(scope.places).toEqualData(TestData.places.places);

  }));

});
