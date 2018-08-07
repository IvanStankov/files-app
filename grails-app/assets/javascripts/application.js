// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require js/jquery-ui-1.10.4.custom
//= require_tree .
//= require_self

$(function() {

    var spinnerService = (function() {
        var spinner = $('#spinner');
        return {
            show: function() {
                spinner.show();
            },

            hide: function() {
                spinner.hide();
            }
        }
    })();

    var popupService = (function() {
        var popup = $("#resultPopup");
        function showPopup(title, content, cssClass) {
            popup.html(content)
                .dialog({
                    title: title,
                    dialogClass: cssClass,
                    resizable: false,
                    modal: true,
                    draggable: false,
                    width: 700,
                    open: function() {
                        // whe close button is focused?
                        $(".ui-dialog-titlebar-close").blur();
                    }
                });
        }

        return {
            showSuccess: function() {
                Array.prototype.push.call(arguments, "success");
                showPopup.apply(null, arguments);
            },
            showError: function() {
                Array.prototype.push.call(arguments, "error");
                showPopup.apply(null, arguments);
            }
        }
    })();

    (function($, SpinnerService, PopupService) {
        Dropzone.options.fileUpload = {
            paramName: "targetFile",
            dictDefaultMessage: "Drop files here to upload or click to select manually",
            init: function() {
                this.on("error", function(file, error, xhr) {

                    if (xhr.status == 415) {
                        PopupService.showError("Wrong content type", `
                            Actual content type: ${error.actualContentType}
                            <div>
                                Expected content types:
                                <ul>
                                    ${error.expectedContentType
                                        .map(contentType => `<li>${contentType}</li>`)
                                        .join('')
                                    }
                                </ul>
                            </div>
                        `);
                        return;
                    }

                    if (xhr.status == 400) {
                        PopupService.showError("Tracking changes found!", `File contains tracked changes: ${error.revisionsNumber}`);
                        return;
                    }

                    PopupService.showError("Error", `
                        Sorry, something went wrong with our server:
                        <div>${error.stacktrace}</div>
                    `);
                });
            },
            success: function() {
                PopupService.showSuccess("Success", "Uploaded file is OK!");
            },
            processing: function() {
                SpinnerService.show();
            },
            complete: function() {
                this.removeAllFiles(true);
                SpinnerService.hide();
            }
        };
    })(jQuery, spinnerService, popupService);
});