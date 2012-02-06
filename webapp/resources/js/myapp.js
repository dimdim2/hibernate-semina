String.prototype.replaceAll = function(from, to) {
	return this.replace(new RegExp(from, "g"), to);
};
String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); };
String.prototype.ltrim = function() { return this.replace(/(^\s*)/g, ""); };
String.prototype.rtrim = function() { return this.replace(/(\s*$)/g, ""); };

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";

    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yy": return d.getFullYear();
            case "y": return (d.getFullYear() % 1000).zf(2);
            case "mm": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mi": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

function disableLinks() {
	objLinks = document.links;
	for (var i = 0; i < objLinks.length; i++) {
		if ((objLinks[i].href.indexOf("updateRefundFlag(") > 0)) {
			objLinks[i].disabled = true;
			objLinks[i].onclick = function() { return false; };
		}
	}
}

Date.prototype.getLastDayOfMonth = function() {
	var year = this.getYear();
	var month = this.getMonth();

	return (new Date((new Date(year, month+1, 1))-1)).getDay();
};

function getLastDateOfMonth(date) {
	var year = date.getFullYear();
	var month = date.getMonth();

	var date = new Date(year, month+1, 1);
	date.setDate(date.getDate() - 1);

	return date;
}

function hideElement(element) {
	element.style.display = 'none';
	element.style.visivility = 'hidden';
}

function displayElement(element) {
	element.style.display = 'block';
	element.style.visivility = 'visible';
}

function createOption(value, label) {
	var option = document.createElement("option");
	option.setAttribute("value", value);
	option.appendChild(document.createTextNode(label));

	return option;
}

function countByte(value) {
	var retVal = 0;
	for(var j=0;j<value.length;j++) {
		if (value.charCodeAt(j) > 255)
			retVal += 2;
		else
			retVal++;
	}
	return retVal;
}

function isNull(value) {
	return (value == null);
}

function isEmpty(value) {
	return (value == "");
}

function isEmptyOrWhitespace(value) {
	return (value == null || value.trim() == "");
}

function isInteger(value) {
	var integerVal = parseInt(value);
	return (value == String(integerVal));
}

function isPositiveInteger(value) {
	var integerVal = parseInt(value);
	return (value == String(integerVal) && integerVal > 0);
}

function isFloat(value) {
	return Number(value) == value;
}

function isAlpha(value) {
	for (var j=0;j<value.length;j++) {
    	if (value.charCodeAt(j) >= 255) {
	        return false;
        }
    }
    return true;
}

function assertNotEmpty(value, message, callback) {
	if (isEmptyOrWhitespace(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertInteger(value, message, callback) {
	if (!isInteger(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertFloat(value, message, callback) {
	if (!isFloat(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertAlpha(value, message, callback) {
	if (!isAlpha(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertByteLength(value, byteLength, message, callback) {
	var length = countByte(value);
	if (length > byteLength) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}