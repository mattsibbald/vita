(function(angular) {
  'use strict';

  var vitaDirectives = angular.module('vitaDirectives');

  vitaDirectives.directive('part', function() {
    function link(scope) {
      scope.title = scope.partData.title || 'Part ' + scope.partData.number;
      scope.chapters = scope.partData.chapters;
    }

    return {
      restrict: 'A',
      scope: {
        partData: '=',
        documentId: '=',
        isOnlyPart: '='
      },
      link: link,
      templateUrl: 'templates/part.html'

    };
  });

})(angular);
