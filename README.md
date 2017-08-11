# scala.akka-http.g8
[Giter8](http://www.foundweekends.org/giter8/) template for standalone Scala akka-http server applications.

This template creates a new SBT based scala project with dependencies for akka, akka-http and json.
The SBT produces cross compiled libraries and a docker image.

# Applying template

Using giter8: `g8 git@github.com:barredijkstra/scala-akka.g8.git`

Using sbt: `sbt new git@github.com:barredijkstra/scala-akka.g8.git`

## Configuration values
When the template is applied via g8 or sbt a number of values must be entered which steer the generated project.
Due to the nature of giter8 they will not appear in the ideal order.
 
- `name` - The name of the project (e.g. as stored in git). For example `my-app`
- `short_name` - The short name of the project, used as jar name, package name, etc. For example `myapp`
- `organization` - The organization (group) as used in dependencies
- `organization_name` - The full name of the organization
- `organization_shortname` - The short name of the organization, used for repositories.
- `organization_website` - The website of the organization.
- `project_year` - The year of project inception
- `description` - The project description
- `package` - The package of the library
- `scala_version` - The main scala version to use
- `sbt_version` - The SBT version to use
- `lib_*_version_` - The dependency versions

# Requirements
The following requirements apply for using the template:

- JDK 8+ installed
- Either Giter8 or SBT 0.13.15+ installed
- Access to the git repository
