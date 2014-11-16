(function(angular) {
  'use strict';

  var vitaControllers = angular.module('vitaControllers');

  vitaControllers.controller('DocumentViewCtrl', ['$scope', 'DocumentViewReceiver', 'Document',
      'DocumentParts', 'DocumentSearch',
      function($scope, DocumentViewReceiver, Document, DocumentParts, DocumentSearch) {

        DocumentViewReceiver.onDocumentId(function(messageData) {
          var documentId = messageData.message;

          Document.get({
            documentId: documentId
          }, function(document) {
            $scope.document = document;
            requestParts(document);
          });
        });

        DocumentViewReceiver.requestDocumentId();

        function requestParts(document) {
          DocumentParts.get({
            documentId: document.id
          }, function(partData) {
            $scope.parts = partData.parts;
          });
        }

        $scope.search = function() {
          DocumentSearch.search({
            documentId: $scope.document.id,
             query: $scope.query
          }, function(response) {
            var occurrences = response.occurrences;
            $scope.resultCount = occurrences.length;
            // TODO highlight occurrences
            // highlightOccurrences(occurrences);
          });
        }
      }]);

})(angular);