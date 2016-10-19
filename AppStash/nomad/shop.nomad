
# There can only be a single job definition per file.
# Create a job with ID and Name 'shop'
job "shop" {
	# Run the job in the global region, which is the default.
	# region = "global"

	# Specify the datacenters within the region this job can run in.
	datacenters = ["dc1"]

	# Service type jobs optimize for long-lived services. This is
	# the default but we can change to batch for short-lived tasks.
	# type = "service"

	# Priority controls our access to resources and scheduling priority.
	# This can be 1 to 100, inclusively, and defaults to 50.
	# priority = 50

	# Restrict our job to only linux. We can specify multiple
	# constraints as needed.
	constraint {
		attribute = "$attr.kernel.name"
		value = "linux"
	}

	# Configure the job to do rolling updates
	update {
		# Stagger updates every 10 seconds
		stagger = "10s"

		# Update a single task at a time
		max_parallel = 1
	}

	# Create a 'cache' group. Each task in the group will be
	# scheduled onto the same machine.
	group "database" {
		# Control the number of instances of this groups.
		# Defaults to 1
		# count = 1

		task "redis" {
			driver = "docker"
			config {
				image = "redis:latest"
			}

		    resources {
				cpu = 500 # 500 Mhz
				memory = 256 # 256MB

				network {
					mbits = 10
					dynamic_ports = ["redis"]
				}
			}
		}

		task "mongo" {
            driver = "mongo"
            config {
                image = "mongo:latest"
            }

            resources {
                cpu = 500 # 500 Mhz
        	    memory = 256 # 256MB

        	    network {
        	        mbits = 10
        		    dynamic_ports = ["mongodb"]
        	    }
            }
        }
	}
}
