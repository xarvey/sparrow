// to be edited.
function sendEmail(receiver, subject, content) {
	var sendgrid = require("sendgrid")(api_user, api_key);
	var email = new sendgrid.Email();

	email.addTo(receiver);
	email.setFrom("no-reply@sparrow.com");
	email.setSubject(subject);
	email.setHtml(content);

	sendgrid.send(email, function(err, json) {
	  if (err) { return console.error(err); }
	  console.log(json);
	});
}