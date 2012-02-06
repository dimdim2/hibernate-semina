jQuery.validator.addMethod("alphanumericspecial", function(value, element) {
	return this.optional(element) || value == value.match(/^[-a-zA-Z0-9_ ]+$/);
}, "Only letters, Numbers & Space/underscore Allowed.");

jQuery.validator.addMethod("alpha", function(value, element) {
	return this.optional(element) || value == value.match(/^[a-zA-Z]+$/);
}, "Only Characters Allowed.");

jQuery.validator.addMethod("alphanumeric", function(value, element) {
	return this.optional(element) || value == value.match(/^[a-z0-9A-Z#]+$/);
}, "Only Characters, Numbers & Hash Allowed.");

$.validator.addMethod('ipAddress', function(value) {
    var ip = /^(\d{1,3}\.){3}\d{1,3}$/;
        return value.match(ip);
    }, 'Invalid IP address');

jQuery.validator.addMethod('macAddress', function(value) {
    var mac = "^([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}$";
    return value.match(mac);
}, ' Invalid MAC Address');

jQuery.validator.addMethod("version", function(value, element) {
	return this.optional(element) || value == value.match(/[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+/);
}, "Invalid Version Format! [ex : 1.0.0.0 /* Release.Major.Minor.Bugfix */]");