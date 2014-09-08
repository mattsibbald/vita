describe('ProfileCtrl', function() {
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
    $httpBackend.expectGET('/documents/doc13a/persons/person8').respond(TestData.singleProfile);

    $routeParams.documentId = 'doc13a';
    $routeParams.personId = 'person8';

    scope = $rootScope.$new();
    ctrl = $controller('ProfileCtrl', {
      $scope: scope
    });
  }));

  it('should get a profile from REST by using the Profiles service', inject(function(TestData) {
    expect(scope.profile).toEqualData({});
    $httpBackend.flush();
    expect(scope.profile).toEqualData(TestData.singleProfile);
  }));

});
